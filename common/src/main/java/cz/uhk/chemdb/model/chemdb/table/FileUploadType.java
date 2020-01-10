package cz.uhk.chemdb.model.chemdb.table;

import javax.inject.Named;
import java.io.Serializable;

@Named
public enum FileUploadType implements Serializable {

    K_DATA(1, "K DATA"),
    INVITRO_DATA(2, "INVITRO DATA");
    //TYPE_3(3, "Typ 3");

    String name;
    int typeId;

    FileUploadType(int id, String name) {
        this.typeId = typeId;
        this.name = name;

    }

    public static FileUploadType findByName(String name) {
        for (FileUploadType type : FileUploadType.values()) {
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

    @Override
    public String toString() {
        return name;
    }
}
