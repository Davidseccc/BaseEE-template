package cz.uhk.chemdb.utils;

import java.io.Serializable;

public enum PermissionRole implements Serializable {

    USER(1, "USER"),
    ADMIN(2, "ADMIN"),
    SUPER_ADMIN(3, "SUPER_ADMIN"),
    CONTRIBUTOR(4, "CONTRIBUTOR");

    String name;
    int id;

    PermissionRole(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
