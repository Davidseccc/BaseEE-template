package cz.uhk.chemdb.model.chemdb.table;

import javax.inject.Named;
import java.io.Serializable;

@Named
public enum ErrorType implements Serializable {

    N_A(1, "(not available)", "\\d{1,3}(?>\\.|\\,)?\\d{1,2}"),
    CONF_95(2, "95% confidence interval", "\\d{1,2},\\d{1,2}-\\d{1,2},\\d{1,2}"),
    SD(3, "SD", "\\d{1,4}(?>\\.|\\,)?\\d{1,2}"),
    SEM(4, "SEM", "\\d{1,4}(?>\\.|\\,)?\\d{1,2}");

    String name;
    int typeId;
    String regex;

    ErrorType(int typeId, String name, String regex) {
        this.typeId = typeId;
        this.name = name;
        this.regex = regex;

    }

    public static ErrorType findByName(String name) {
        for (ErrorType type : ErrorType.values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String toString() {
        return name;
    }
}
