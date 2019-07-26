package cz.uhk.chemdb.bean.view.datamodel;

import org.apache.commons.lang3.tuple.Pair;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.criteria.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class GenericLazyModel<T> extends LazyDataModel<T> {

    private final EntityManager em;
    private final Class<T> clazz;
    private final String primaryKey;

    private List<Pair<String, List<Object>>> transformedFilters;
    private int cachedCount = -1;
    private int cachedFiltersHashcode;

    GenericLazyModel(EntityManager em, Class<T> clazz) {
        this.em = em;
        this.clazz = clazz;
        this.primaryKey = getPrimaryKeyFromClass(clazz);
    }

    /**
     * @param cb
     * @param root
     * @return Predicate to use if additional filtering applies.
     */
    Predicate getInitPredicate(CriteriaBuilder cb, Root<T> root) {
        return null;
    }

    @Override
    public List<T> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
        String sortField = multiSortMeta != null && !multiSortMeta.isEmpty() ? multiSortMeta.get(0).getSortField() : null;
        SortOrder sortOrder = multiSortMeta != null && !multiSortMeta.isEmpty() ? multiSortMeta.get(0).getSortOrder() : SortOrder.ASCENDING;

        transformedFilters = new ArrayList<>();

        for (String filterField : filters.keySet()) {
            Object filterValue = filters.get(filterField);
            transformedFilters.add(getTransformedFilterValue(filterField, filterValue));
        }

        int hashCode = transformedFilters.hashCode();
        if (hashCode != cachedFiltersHashcode) {
            cachedFiltersHashcode = hashCode;
            cachedCount = -1;
        }

        return getResultList(first, pageSize, sortField, sortOrder, transformedFilters);
    }

    private Pair<String, List<Object>> getTransformedFilterValue(String filterField, Object filterValue) {
        List<Object> filterValues = new ArrayList<>();

        try {
            final Field field = clazz.getDeclaredField(filterField);
            final Class<?> type = field.getType();

            if (type.isEnum()) {
                for (Object enumConstant : type.getEnumConstants()) {
                    if (filterValue instanceof String[]) {
                        for (String oneFilterValue : ((String[]) filterValue)) {
                            if (enumConstant.toString().equals(oneFilterValue)) {
                                filterValues.add(enumConstant);
                            }
                        }
                    } else if (enumConstant.toString().equals(filterValue)) {
                        filterValues.add(enumConstant);
                    }
                }

                return Pair.of(filterField, filterValues);
            }
        } catch (NoSuchFieldException ignored) {
        }

        if (filterValue instanceof String[]) {
            filterValues.addAll(Arrays.asList((String[]) filterValue));
        } else {
            filterValues.add(filterValue);
        }

        return Pair.of(filterField, filterValues);
    }

    @Override
    public int getRowCount() {
        if (cachedCount != -1) {
            return cachedCount;
        }

        cachedCount = getCount(transformedFilters);
        return cachedCount;
    }

    @Override
    public T getRowData(String rowKey) {
        if (primaryKey == null) {
            // PK not found
            return null;
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> root = cq.from(clazz);

        Path<String> path = root.get(primaryKey);
        cq.where(cb.equal(path, rowKey));

        return em.createQuery(cq).getSingleResult();
    }

    private int getCount(List<Pair<String, List<Object>>> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> root = cq.from(clazz);

        Predicate initPredicate = getInitPredicate(cb, root);
        if (initPredicate != null) {
            cq.where(initPredicate, getFilterCondition(cb, root, filters));
        } else {
            cq.where(getFilterCondition(cb, root, filters));
        }

        cq.select(cb.count(root));

        return em.createQuery(cq).getSingleResult().intValue();
    }

    private List<T> getResultList(int first, int pageSize, String sortField, SortOrder sortOrder, List<Pair<String, List<Object>>> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> root = cq.from(clazz);

        Predicate initPredicate = getInitPredicate(cb, root);

        if (initPredicate != null) {
            cq.where(initPredicate, getFilterCondition(cb, root, filters));
        } else {
            cq.where(getFilterCondition(cb, root, filters));
        }

        if (sortField != null) {
            Path<String> pathFromFieldKey = getPathFromFieldKey(sortField, root);

            if (sortOrder == SortOrder.ASCENDING) {
                cq.orderBy(cb.asc(pathFromFieldKey));
            } else if (sortOrder == SortOrder.DESCENDING) {
                cq.orderBy(cb.desc(pathFromFieldKey));
            }
        }

        return em.createQuery(cq).setFirstResult(first).setMaxResults(pageSize).getResultList();
    }

    private Predicate getFilterCondition(CriteriaBuilder cb, Root<T> root, List<Pair<String, List<Object>>> filters) {
        Predicate andPredicate = cb.conjunction();

        if (filters != null) {
            for (Pair<String, List<Object>> filter : filters) {
                Path<String> path = getPathFromFieldKey(filter.getLeft(), root);

                if (filter.getRight().isEmpty()) {
                    continue;
                }

                Predicate innerPredicate = cb.disjunction();

                if (filter.getRight().get(0) instanceof String) {
                    for (Object filterValueObject : filter.getRight()) {
                        String value = "%" + filterValueObject + "%";
                        innerPredicate = cb.or(innerPredicate, cb.like(cb.lower(path), value.toLowerCase()));
                    }
                } else {
                    innerPredicate = path.in(filter.getRight());
                }

                andPredicate = cb.and(andPredicate, innerPredicate);
            }
        }
        return andPredicate;
    }

    private Path<String> getPathFromFieldKey(String fieldKey, Root<?> root) {
        String[] keys = fieldKey.split("\\.");
        Path<String> path = root.get(keys[0]);

        for (int i = 1; i < keys.length; i++) {
            path = path.get(keys[i]);
        }

        return path;
    }

    private String getPrimaryKeyFromClass(Class<T> clazz) {
        String primaryKey = null;

        for (Field field : clazz.getDeclaredFields()) {
            for (Annotation annotation : field.getAnnotations()) {
                if (annotation instanceof Id) {
                    if (primaryKey == null) {
                        primaryKey = field.getName();
                    } else {
                        // composite PK, cannot do
                        return null;
                    }
                }
            }
        }

        return primaryKey;
    }
}
