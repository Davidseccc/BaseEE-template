package cz.uhk.chemdb.bean.view.component;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import java.util.HashMap;
import java.util.Map;

public abstract class StatefulColumnManager {

    private Map<Integer, Boolean> columnsVisible = new HashMap<>();

    public boolean isColumnVisible(int index) {
        return isColumnVisible(index, true);
    }

    public boolean isColumnVisible(int index, boolean defaultValue) {
        if (columnsVisible.get(index) == null) {
            return defaultValue;
        } else {
            return columnsVisible.get(index);
        }
    }

    public void onToggle(ToggleEvent e) {
        columnsVisible.put((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }
}
