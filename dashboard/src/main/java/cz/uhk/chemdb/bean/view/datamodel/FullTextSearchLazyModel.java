package cz.uhk.chemdb.bean.view.datamodel;

import cz.uhk.chemdb.bean.view.FullTextSearch;
import cz.uhk.chemdb.model.chemdb.table.Compound;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FullTextSearchLazyModel extends GenericLazyModel<Compound> {
    private final List<FullTextSearch.Predicate> predicates;

    public FullTextSearchLazyModel(EntityManager entityManager, List<FullTextSearch.Predicate> predicates) {
        super(entityManager, Compound.class);
        this.predicates = predicates;
    }

    @Override
    Predicate getInitPredicate(CriteriaBuilder cb, Root<Compound> root) {
        Predicate res = null;
        int index = predicates.size() - 1;
        if (predicates.size() > 0) {
            if (index == 0) {
                FullTextSearch.Predicate first = predicates.get(index);
                res = cb.not(cb.not(getPredicate(cb, root, first)));
            }
            while (index > 0) {
                FullTextSearch.Predicate last = predicates.get(index);
                if (res == null) { //connect first two elements AND(LAST, LAST-1)
                    FullTextSearch.Predicate prew = predicates.get(index - 1);
                    Expression<Boolean> e1 = getPredicate(cb, root, last);
                    Expression<Boolean> e2 = getPredicate(cb, root, prew);
                    res = cb.and(e1, e2);
                    index = index - 2;
                } else {        //just connect to the rest of results
                    Expression<Boolean> e1 = getPredicate(cb, root, last);
                    res = cb.and(e1, res);
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
                res = cb.equal(getAttribute(root, predicate.getField().getAttributeName()), validateValue(predicate));
                break;
            case is_not:
                res = cb.notEqual(getAttribute(root, predicate.getField().getAttributeName()), validateValue(predicate));
                break;
            case is_one_of:
                List<String> values = Arrays.asList(predicate.getValue().split(","));
                res = cb.and(getAttribute(root, predicate.getField().getAttributeName()).in(values.isEmpty() ? Collections.singletonList(-1) : values));
                break;
            case is_not_one_of:
                List<String> value = Arrays.asList(predicate.getValue().split(","));
                res = cb.not(getAttribute(root, predicate.getField().getAttributeName()).in(value.isEmpty() ? Collections.singletonList(-1) : value));
                break;
            case gt:
                res = cb.greaterThanOrEqualTo(!predicate.getField().getAttributeName().contains(".") ?
                        root.get(predicate.getField().getAttributeName()) :
                        root.get(predicate.getField().getAttributeName().substring(0, predicate.getField()
                                .getAttributeName().indexOf('.'))).get(predicate.getField().getAttributeName()
                                .substring(predicate.getField().getAttributeName().indexOf('.') + 1)), predicate.getValue());
                break;
            case lt:
                res = cb.lessThanOrEqualTo(!predicate.getField().getAttributeName().contains(".") ?
                        root.get(predicate.getField().getAttributeName()) :
                        root.get(predicate.getField().getAttributeName().substring(0, predicate.getField()
                                .getAttributeName().indexOf('.'))).get(predicate.getField().getAttributeName()
                                .substring(predicate.getField().getAttributeName().indexOf('.') + 1)), predicate.getValue());
                break;
            case like:
                String val = "%" + predicate.getValue().toLowerCase() + "%";
                res = cb.like(
                        !predicate.getField().getAttributeName().contains(".") ?
                                root.get(predicate.getField().getAttributeName()) :
                                root.get(predicate.getField().getAttributeName().substring(0, predicate.getField()
                                        .getAttributeName().indexOf('.'))).get(predicate.getField().getAttributeName()
                                        .substring(predicate.getField().getAttributeName().indexOf('.') + 1)), val);
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


    private Expression<?> getAttribute(Root<Compound> root, String attributeName) {
        String[] params = attributeName.split("\\.");
        Path<Compound> res = root;

        if (params.length >= 1) {
            res = root.get(params[0]);
        }
        if (params.length > 1) {
            for (int i = 1; i < params.length; i++) {
                res = res.get(params[i]);
            }
        }
        return res;
    }

}
