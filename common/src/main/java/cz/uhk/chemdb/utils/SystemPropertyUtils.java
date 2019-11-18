package cz.uhk.chemdb.utils;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemPropertyUtils {

    public static boolean isProduction() {
        String debug = System.getProperty("debug");
        return debug == null || !debug.equals("true");
    }

    public static String getUploadsPath() {
        String file_upload_path = System.getProperty("FILE_UPLOAD_PATH");
        if (StringUtils.isEmpty(file_upload_path)) {
            Logger.getGlobal().log(Level.SEVERE, String.format("Property FILE_UPLOAD_PATH is not set. " +
                    "Used default directory to upload documents (%s). Please add FILE_UPLOAD_PATH into system properties.", System.getProperty("java.io.tmpdir")));
            return System.getProperty("java.io.tmpdir");
        }
        return file_upload_path.endsWith(File.separator) ? file_upload_path : file_upload_path + File.separator;
    }

}
