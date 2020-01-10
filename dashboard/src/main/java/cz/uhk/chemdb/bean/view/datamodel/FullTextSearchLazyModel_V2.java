package cz.uhk.chemdb.bean.view.datamodel;

import cz.uhk.chemdb.bean.view.FullTextSearch;
import cz.uhk.chemdb.model.chemdb.table.Descriptor;
import org.apache.commons.lang3.tuple.Pair;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.criteria.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class FullTextSearchLazyModel_V2<Compound> extends LazyDataModel<Compound> {

    private final EntityManager em;
    private final Class<Compound> clazz;
    private final String primaryKey;

    private List<Pair<String, List<Object>>> transformedFilters;
    private int cachedCount = -1;
    private int cachedFiltersHashcode;
    private SelectType selectType;
    private JoinType joinType;
    private List<FullTextSearch.Predicate> predicates;


    FullTextSearchLazyModel_V2(EntityManager em, Class<Compound> clazz, SelectType selectType, JoinType joinType) {
        this.em = em;
        this.clazz = clazz;
        this.primaryKey = getPrimaryKeyFromClass(clazz);
        this.selectType = selectType;
        this.joinType = joinType;
    }

    FullTextSearchLazyModel_V2(EntityManager em, Class<Compound> clazz) {
        this.em = em;
        this.clazz = clazz;
        this.primaryKey = getPrimaryKeyFromClass(clazz);
        this.selectType = SelectType.ALL;
        this.joinType = JoinType.AND;
    }

    public FullTextSearchLazyModel_V2(EntityManager em, Class<Compound> clazz, List<FullTextSearch.Predicate> predicates, SelectType selectType, JoinType joinType) {
        this.em = em;
        this.clazz = clazz;
        this.primaryKey = getPrimaryKeyFromClass(clazz);
        this.selectType = SelectType.ALL;
        this.joinType = JoinType.AND;
        this.predicates = predicates;
    }


    /**
     * @param cb
     * @param root
     * @return Predicate to use if additional filtering applies.
     */
    Predicate getInitPredicate(CriteriaBuilder cb, Root<Compound> root) {
        Predicate res = null;

        int index = predicates.size() - 1;
        if (predicates.size() > 0) {
            if (index == 0) {
                FullTextSearch.Predicate first = predicates.get(index);
                res = cb.not(cb.not(getPredicate(cb, root, first)));
            }
            while (index >= 0) {
                FullTextSearch.Predicate last = predicates.get(index);
                if (res == null) { //connect first two elements AND(LAST, LAST-1)
                    FullTextSearch.Predicate prew = predicates.get(index - 1);
                    Expression<Boolean> e1 = getPredicate(cb, root, last);
                    Expression<Boolean> e2 = getPredicate(cb, root, prew);
                    res = getJoinType() == JoinType.OR ? cb.or(e1, e2) : cb.and(e1, e2);
                    index = index - 2;
                } else {        //just connect to the rest of results
                    Expression<Boolean> e1 = getPredicate(cb, root, last);
                    res = getJoinType() == JoinType.OR ? cb.or(e1, res) : cb.and(e1, res);

                    index = index - 1;
                }
            }
        }
        return res;

    }

    private Expression<Boolean> getPredicate(CriteriaBuilder cb, Root<Compound> root, FullTextSearch.Predicate predicate) {
        Predicate res = null;
        switch (predicate.getOperator()) {
            case is:
                res = cb.equal(getPredicateAttributes(root, predicate), validateValue(predicate));
                break;
            case is_not:
                res = cb.notEqual(getPredicateAttributes(root, predicate), validateValue(predicate));
                break;
            case is_one_of:
                List<String> values = Arrays.asList(predicate.getValue().split(","));
                res = cb.and(getPredicateAttributes(root, predicate).in(values.isEmpty() ? Collections.singletonList(-1) : values));
                break;
            case is_not_one_of:
                List<String> value = Arrays.asList(predicate.getValue().split(","));
                res = cb.not(getPredicateAttributes(root, predicate).in(value.isEmpty() ? Collections.singletonList(-1) : value));
                break;
            case gt:
                res = cb.greaterThanOrEqualTo(getPredicateAttributes(root, predicate), predicate.getValue());
                break;
            case lt:
                res = cb.lessThanOrEqualTo(getPredicateAttributes(root, predicate), predicate.getValue());
                break;
            case like:
                String val = "%" + predicate.getValue().toLowerCase() + "%";
                res = cb.like(getPredicateAttributes(root, predicate), val);
                break;
        }
        return res;
    }

    private Object validateValue(FullTextSearch.Predicate value) {
        switch (value.getField().getDataType()) {
            case STRING:
                return value.getValue();
            case INTEGER:
                return Integer.parseInt(value.getValue());
            case FLOAT:
                return Float.parseFloat(value.getValue());
            case DOUBLE:
                return Double.parseDouble(value.getValue());
            case BOOLEAN:
                return Boolean.parseBoolean(value.getValue());
            default:
                return value.getValue();
        }
    }

    private Path<String> getPredicateAttributes(Root<Compound> root, FullTextSearch.Predicate predicate) {
        String[] params = predicate.getField().getAttributeName().split("\\.");
        if (params.length == 1) {
            return root.get(params[0]);
        } else {
            if (params.length == 2 && root.get(params[0]) != null) {
                Join<Object, Object> join = root.join(params[0], javax.persistence.criteria.JoinType.LEFT);
                return join.get(params[params.length - 1]);
            }
            if (params.length == 3) {
                Join<Object, Object> join = root.join(params[0], javax.persistence.criteria.JoinType.LEFT)
                        .join(params[1], javax.persistence.criteria.JoinType.LEFT);
                return join.get(params[params.length - 1]);
            }
        }
        return null;
    }

    @Override
    public List<Compound> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
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
    public Compound getRowData(String rowKey) {
        if (primaryKey == null) {
            // PK not found
            return null;
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Compound> cq = cb.createQuery(clazz);
        Root<Compound> root = cq.from(clazz);
        Join<Compound, Descriptor> joinedRoot = root.join("descriptor", javax.persistence.criteria.JoinType.LEFT);
        joinedRoot.join("synonyms", javax.persistence.criteria.JoinType.LEFT);
        Path<String> path = root.get(primaryKey);

        cq.where(cb.equal(path, rowKey));

        return em.createQuery(cq).getSingleResult();
    }

    private int getCount(List<Pair<String, List<Object>>> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        if (selectType == SelectType.DISTINCT) {
            cq.distinct(true);
        }
        Root<Compound> root = cq.from(clazz);
        Predicate initPredicate = getInitPredicate(cb, root);
        if (initPredicate != null) {
            cq.where(initPredicate, getFilterCondition(cb, root, filters));
        } else {
            cq.where(getFilterCondition(cb, root, filters));
        }

        cq.select(cb.count(root));

        return em.createQuery(cq).getSingleResult().intValue();
    }

    private List<Compound> getResultList(int first, int pageSize, String sortField, SortOrder sortOrder, List<Pair<String, List<Object>>> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Compound> cq = cb.createQuery(clazz);
        if (selectType == SelectType.DISTINCT) {
            cq = cq.distinct(true);
        }
        Root<Compound> root = cq.from(clazz);

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

    private Predicate getFilterCondition(CriteriaBuilder cb, Root<Compound> root, List<Pair<String, List<Object>>> filters) {
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

    private String getPrimaryKeyFromClass(Class<Compound> clazz) {
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

    public JoinType getJoinType() {
        return joinType;
    }

    public SelectType getSelectType() {
        return selectType;
    }
}
