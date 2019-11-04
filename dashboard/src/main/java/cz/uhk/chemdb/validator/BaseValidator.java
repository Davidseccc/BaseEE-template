package cz.uhk.chemdb.validator;

public abstract class BaseValidator {

    public boolean isValid(String string) {
        return true;
    }

    public boolean isValid(String value, String regex) {
        return value.matches(regex);
    }
}


