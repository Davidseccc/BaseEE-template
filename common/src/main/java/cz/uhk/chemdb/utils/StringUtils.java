package cz.uhk.chemdb.utils;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static final Locale CZECH_LOCALE = new Locale("cs", "CZ");

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }


    public static String emptyToNull(String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        } else {
            return string;
        }
    }
}
