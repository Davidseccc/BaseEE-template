package cz.uhk.chemdb.enums;

import javax.inject.Named;
import java.io.Serializable;

@Named
public enum SearchField implements Serializable {

    K(1, "k", DataType.INTEGER),
    Smiles(2, "smiles", DataType.STRING),
    OriginalCodename(3, "originalCodename", DataType.STRING),
    ION(4, "ion", DataType.STRING),
    MW(5, "mw", DataType.DOUBLE),
    NOTES(6, "notes", DataType.STRING),
    OWNER_name(7, "owner.name", DataType.STRING),
    MELTING_POINT_isOil(8, "meltingPoint.oil", DataType.BOOLEAN),
    MELTING_POINT_temperatureFrom(9, "meltingPoint.temperatureFrom", DataType.FLOAT),
    MELTING_POINT_temperatureTo(10, "meltingPoint.temperatureTo", DataType.FLOAT);

    String attributeName;
    int id;
    DataType dataType;

    SearchField(int id, String attributeName, DataType dataType) {
        this.id = id;
        this.attributeName = attributeName;
        this.dataType = dataType;

    }

    public static SearchField findByAttributeName(String name) {
        for (SearchField type : SearchField.values()) {
            if (type.attributeName.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return attributeName;
    }

    public enum DataType {
        STRING(),
        INTEGER(),
        BOOLEAN(),
        DOUBLE(),
        FLOAT()
    }
}
