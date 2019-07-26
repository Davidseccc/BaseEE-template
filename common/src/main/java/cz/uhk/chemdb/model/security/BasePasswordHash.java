package cz.uhk.chemdb.model.security;

public abstract class BasePasswordHash {
    public abstract String getPasswordHash(String password, String salt);
}
