package cz.uhk.chemdb.utils;

import cz.uhk.chemdb.model.chemdb.table.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class UserPermission implements Serializable {
    private Map<PermissionRole, Set<PermissionAttribute>> permissions = new HashMap<>();

    public static Set<PermissionAttribute> setPermissions(User user) {
        Set out = new HashSet<>();
        for (PermissionAttribute a : PermissionAttribute.values()) {
            out.add(a);
        }
        return out;
    }

    public Map<PermissionRole, Set<PermissionAttribute>> getPermissions() {
        if (permissions.isEmpty()) {
        }
        return permissions;
    }

    public Map<PermissionRole, Set<PermissionAttribute>> addPermission(PermissionRole role, PermissionAttribute attribute) {
        if (!permissions.containsKey(role)) {
            permissions.put(role, new HashSet<>());
        }
        permissions.get(role).add(attribute);
        return permissions;
    }

    public Map<PermissionRole, Set<PermissionAttribute>> addPermission(PermissionRole role, PermissionAttribute[] attributes) {
        if (!permissions.containsKey(role)) {
            permissions.put(role, new HashSet<>());
        }
        if (attributes.length > 0) {
            permissions.get(role).addAll(Arrays.asList(attributes));
        }
        return permissions;
    }

    public Map<PermissionRole, Set<PermissionAttribute>> removePermission(PermissionRole role, PermissionAttribute attribute) {
        if (!permissions.containsKey(role)) {
            Logger.getGlobal().log(Level.SEVERE, "Cannot delete permission. ROLE" + role + "is not present.");

        }
        permissions.get(role).remove(attribute);
        return permissions;
    }

    public Map<PermissionRole, Set<PermissionAttribute>> removePermission(PermissionRole role, PermissionAttribute[] attributes) {
        if (!permissions.containsKey(role)) {
            Logger.getGlobal().log(Level.SEVERE, "Cannot delete permission. ROLE" + role + "is not present.");

        }
        for (PermissionAttribute attribute : attributes) {
            permissions.get(role).remove(attribute);
        }
        return permissions;
    }

    public boolean hasPermission(PermissionAttribute attribute, PermissionRole role) {
        if (attribute != null && permissions.containsKey(attribute)) {
            return role != null && permissions.get(attribute).contains(role);
        }
        return false;
    }

    public PermissionRole getUserRole(User user) {
        if (user.isSuperAdmin()) {
            return PermissionRole.SUPER_ADMIN;
        } else if (user.isAdmin()) {
            return PermissionRole.ADMIN;
        } else if (user.isContributor()) {
            return PermissionRole.CONTRIBUTOR;
        } else {
            return PermissionRole.USER;
        }
    }

    public boolean hasPermission(PermissionAttribute attribute, User user) {
        PermissionRole role = getUserRole(user);
        return hasPermission(attribute, role);
    }


}

