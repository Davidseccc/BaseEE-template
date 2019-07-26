package cz.uhk.chemdb.model.chemdb.builder;

import cz.uhk.chemdb.model.chemdb.table.User;

import java.time.LocalDateTime;

public final class UserBuilder {
    private static User user;

    public UserBuilder(String email, String password) {
        user = new User(email, password);
    }

    public static UserBuilder createUserBuilder(String email, String password) {
        return new UserBuilder(email, password);
    }

    public UserBuilder setName(String name) {
        user.setName(name);
        return this;
    }

    public UserBuilder setToken(String token) {
        user.setToken(token);
        return this;
    }

    public UserBuilder setAdmin(boolean admin) {
        user.setAdmin(admin);
        return this;
    }

    public UserBuilder setSuperAdmin(boolean superAdmin) {
        user.setSuperAdmin(superAdmin);
        return this;
    }

    public UserBuilder setContributor(boolean contributor) {
        user.setContributor(contributor);
        return this;
    }

    public UserBuilder setDeletedAd(LocalDateTime deletedAt) {
        user.setDeletedAt(deletedAt);
        return this;
    }

    public User build() {
        return user;
    }
}