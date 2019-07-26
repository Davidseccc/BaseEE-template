package cz.uhk.chemdb.util;

import org.primefaces.PrimeFaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogUtils {

    public static void openPageAsDialog(String path) {
        PrimeFaces.current().dialog().openDynamic(path, getDialogOptions(), null);
    }

    public static void openPageAsDialog(String path, Long id) {
        openPageAsDialog(path, String.valueOf(id));
    }

    public static void openPageAsDialog(String path, String id) {
        HashMap<String, List<String>> params = new HashMap<>();
        List<String> idList = new ArrayList<>();
        idList.add(id);
        params.put("id", idList);

        PrimeFaces.current().dialog().openDynamic(path, getDialogOptions(), params);
    }

    public static void openPageAsDialog(String path, List<String> idList) {
        HashMap<String, List<String>> params = new HashMap<>();
        params.put("idList", idList);

        PrimeFaces.current().dialog().openDynamic(path, getDialogOptions(), params);
    }

    private static Map<String, Object> getDialogOptions() {
        return getDialogOptions(null);
    }

    private static Map<String, Object> getDialogOptions(Integer width) {
        Map<String, Object> options = new HashMap<>();

        options.put("modal", true);
        options.put("responsive", true);

        if (width != null) {
            options.put("width", String.valueOf(width));
        }

        return options;
    }

    public static void closeDialog() {
        PrimeFaces.current().dialog().closeDynamic(null);
    }
}
