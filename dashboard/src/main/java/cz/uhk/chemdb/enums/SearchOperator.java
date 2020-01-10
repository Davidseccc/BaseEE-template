package cz.uhk.chemdb.enums;

import javax.inject.Named;
import java.io.Serializable;

@Named
public enum SearchOperator implements Serializable {

    is(1, "is"),
    is_not(2, "is not"),
    is_one_of(3, "is one of"),
    is_not_one_of(4, "is not one of"),
    does_not_exists(6, "does not exists"),
    gt(7, "greater than"),
    lt(8, "lower than"),
    like(9, "like");

    String name;
    int id;

    SearchOperator(int id, String name) {
        this.id = id;
        this.name = name;

    }

    public static SearchOperator findByName(String name) {
        for (SearchOperator type : SearchOperator.values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public static SearchOperator[] getOperatorFor(SearchField.DataType dataType) {
        System.out.println(dataType.name());
        switch (dataType) {
            case STRING:
                return new SearchOperator[]{is, is_not, like, is_one_of, is_not_one_of};
            case INTEGER:
                return new SearchOperator[]{gt, lt};
            case DOUBLE:
                return new SearchOperator[]{gt, lt};
            case FLOAT:
                return new SearchOperator[]{gt, lt};
            case BOOLEAN:
                return new SearchOperator[]{is, is_not};
            default:
                return SearchOperator.values();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
