package cz.uhk.chemdb.filter;

import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;

@Named
public abstract class BaseCompareFilter implements Serializable {

    public boolean gtOrEqual(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        if (value == null) {
            return false;
        }
        if (value instanceof Double) {
            return ((Comparable) value).compareTo(Double.valueOf(filterText)) > 0;
        } else if (value instanceof Float) {
            return ((Comparable) value).compareTo(Float.valueOf(filterText)) > 0;
        } else if (value instanceof Integer) {
            return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
        } else if (value instanceof Long) {
            return ((Comparable) value).compareTo(Long.valueOf(filterText)) > 0;
        }
        return false;
    }


}
