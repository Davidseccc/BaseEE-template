package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.bean.CompoundSelector;
import cz.uhk.chemdb.bean.view.datamodel.FullTextSearchLazyModel;
import cz.uhk.chemdb.bean.view.datamodel.GenericLazyModel;
import cz.uhk.chemdb.bean.view.datamodel.JoinType;
import cz.uhk.chemdb.bean.view.datamodel.SelectType;
import cz.uhk.chemdb.enums.SearchField;
import cz.uhk.chemdb.enums.SearchOperator;
import cz.uhk.chemdb.model.chemdb.repositories.CompoundRepository;
import cz.uhk.chemdb.model.chemdb.table.Compound;
import cz.uhk.chemdb.utils.StringUtils;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Named
@ViewScoped
public class FullTextSearch implements Serializable {

    @Inject
    CompoundSelector compoundSelector;

    @Inject
    CompoundRepository compoundRepository;
    List<Compound> compounds;
    @PersistenceContext
    private EntityManager entityManager;
    private List<Predicate> predicates;
    private GenericLazyModel<Compound> compoundLazyModel;
    private List<Compound> filteredCompounds;
    private Predicate predicate;
    private SelectType selectType;
    private JoinType joinType;
    SearchOperator[] searchOperators;

    private String searchString;
    private boolean isNumber = false;
    private int rowCount;
    private boolean or = false;

    public static String getTagStyle(int i) {
        String[] styles = new String[]{"label-red", "label-blue", "label-yellow", "label-green", "label-grey"};
        return styles[i % 5];
    }

    @PostConstruct
    public void init() {
        predicates = new ArrayList<>();
        predicate = new Predicate();
        searchOperators = SearchOperator.values();
        loadResults();
    }

    public List<String> startSearch() {
        if (!StringUtils.isEmpty(searchString)) {
            return startSearch(searchString);
        }
        return null;
    }

    public List<String> startSearch(String query) {
        List<Compound> compounds = compoundRepository.fullTextSearch(query);
        List<String> results = new ArrayList<>();
        for (Compound c : compounds) {
            results.add(c.toString());
        }
        return results;
    }

    public void startFullTextSearch() {
        if (StringUtils.isNotEmpty(searchString)) {
            loadPredicates();
        }
        loadResults();
    }

    public void loadPredicates() {
        for (SearchField searchField : SearchField.values()) {
            if (searchField.getDataType() == SearchField.DataType.STRING) {
                Predicate p = new Predicate();
                p.setField(searchField);
                p.setOperator(SearchOperator.like);
                p.setValue(searchString);
                predicates.add(p);
            }
            this.selectType = SelectType.ALL;
            this.joinType = JoinType.OR;
        }
    }

    public void onPredicateChange() {
        if (predicate != null && predicate.getField() != null)
            searchOperators = SearchOperator.getOperatorFor(predicate.getField().getDataType());
        else
            searchOperators = SearchOperator.values();
    }

    public void loadResults() {
        rowCount = 0;
        System.out.println("loadResults" + getPredicates().size());
        compoundLazyModel = new FullTextSearchLazyModel(entityManager, predicates, selectType, joinType);
        rowCount = compoundLazyModel.getRowCount();
        compounds = compoundLazyModel.load(0, rowCount, null, new HashMap<>());
        System.out.println(rowCount);
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public List<Compound> getCompounds() {
        return compounds;
    }

    public void setCompounds(List<Compound> compounds) {
        this.compounds = compounds;
    }

    public CompoundRepository getCompoundRepository() {
        return compoundRepository;
    }

    public GenericLazyModel<Compound> getCompoundLazyModel() {
        return compoundLazyModel;
    }

    public void setCompoundLazyModel(GenericLazyModel<Compound> compoundLazyModel) {
        this.compoundLazyModel = compoundLazyModel;
    }

    public List<Compound> getFilteredCompounds() {
        return filteredCompounds;
    }

    public void setFilteredCompounds(List<Compound> filteredCompounds) {
        this.filteredCompounds = filteredCompounds;
    }

    public List<Predicate> getPredicates() {
        return predicates;
    }

    public void setPredicates(List<Predicate> predicates) {
        this.predicates = predicates;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

    public void addPredicate() {
        predicates.add(predicate);
        predicate = new Predicate();
    }

    public void remove(Predicate predicate) {
        predicates.remove(predicate);
        loadResults();
    }

    public class Predicate {
        String name;
        SearchField field;
        SearchOperator operator;
        String value;

        @Override
        public String toString() {
            if (!StringUtils.isEmpty(name)) {
                return name;
            }
            return String.format("%s %s %s", field, operator, value);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public SearchOperator getOperator() {
            return operator;
        }

        public void setOperator(SearchOperator operator) {
            this.operator = operator;
        }

        public SearchOperator[] getOperators() {
            return searchOperators;
        }

        public SearchField getField() {
            return field;
        }

        public void setField(SearchField field) {
            this.field = field;
        }

        public SearchField[] getFields() {
            return SearchField.values();
        }
    }

    public SelectType getSelectType() {
        return selectType;
    }

    public JoinType getJoinType() {
        return joinType;
    }
}
