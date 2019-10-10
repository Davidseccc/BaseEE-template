package cz.uhk.chemdb.model.chemdb.table;

import javax.ejb.Singleton;
import java.io.Serializable;

@Singleton
public enum AttributeType implements Serializable {

    TEXT(1, "TEXT"),
    NUMBER(2, "ČÍSLO"),
    PROCENTA(3, "PROCENTA"),
    STAT_ODCHYLKA(4, "+-"),
    ATTACHEMENT(4, "PŘÍLOHA");

    int id;
    String name;


    AttributeType(int id, String name) {
        this.id = id;
        this.name = name;

    }

    @Override
    public String toString() {
        return name;
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
}
