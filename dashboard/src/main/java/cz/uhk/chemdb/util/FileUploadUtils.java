package cz.uhk.chemdb.util;

import org.primefaces.model.UploadedFile;

import javax.ejb.Singleton;

@Singleton
public class FileUploadUtils {
    //public static final String FILE_UPLOAD_PATH = System.getProperty("FileUploadPath");
    public static final String FILE_UPLOAD_PATH = "/Users/davidsec/Downloads/test/";

    public void saveUploadedFile(UploadedFile file, String name) {
        try {
            file.write(FILE_UPLOAD_PATH + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
