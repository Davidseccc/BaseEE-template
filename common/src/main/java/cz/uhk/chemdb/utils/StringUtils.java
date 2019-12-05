package cz.uhk.chemdb.utils;

import javax.inject.Named;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
public class StringUtils {

    public static final Locale CZECH_LOCALE = new Locale("cs", "CZ");

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_DOI_REGEX =
            Pattern.compile("\\b(10[.][0-9]{4,}(?:[.][0-9]+)*/(?:(?![\"&\\'<>])\\S)+)\\b", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static boolean validateDOI(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = VALID_DOI_REGEX.matcher(email);
        return matcher.find();
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }


    public static String emptyToNull(String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        } else {
            return string;
        }
    }

    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    public static Number getNumber(String string, Object number) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        char separator = new DecimalFormatSymbols(Locale.getDefault()).getDecimalSeparator();
        string = separator == '.' ? string.replace(",", ".") : string.replace(".", ",");
        int iofs = string.indexOf(separator) != -1 ? string.indexOf(separator) : string.length();
        if (number instanceof Double) return Double.parseDouble(string);
        else if (number instanceof Integer) {
            return Integer.parseInt(string.substring(0, iofs));
        } else if (number instanceof Float) return Float.parseFloat(string);
        else if (number instanceof Long) {
            return Long.parseLong(string.substring(0, iofs));
        } else {
            return Long.parseLong(string.substring(0, iofs));
        }
    }
}
