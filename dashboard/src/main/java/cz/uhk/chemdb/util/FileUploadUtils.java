package cz.uhk.chemdb.util;

import cz.uhk.chemdb.bean.UserManager;
import cz.uhk.chemdb.model.chemdb.repositories.UploadedFileRepository;
import cz.uhk.chemdb.utils.DateFormats;
import org.primefaces.model.UploadedFile;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDateTime;

@Singleton
public class FileUploadUtils {
    @Inject
    private UploadedFileRepository uploadedFileRepository;
    @Inject
    private DateFormats dateFormats;
    @Inject
    private UserManager userManager;
    @Inject
    private LogUtils logUtils;

    //public static final String FILE_UPLOAD_PATH = System.getProperty("FileUploadPath");
    public static final String FILE_UPLOAD_PATH = "/Users/davidsec/Downloads/test/";

    private static void mkDirIfNotExist(String directory) {
        File dir = new File(directory);
        if (!dir.exists()) dir.mkdirs();
    }

    public String saveUploadedFile(UploadedFile file) {
        String hash = null;
        cz.uhk.chemdb.model.chemdb.table.UploadedFile uploadedFile = createUploadedFile(file);
        try {
            mkDirIfNotExist(uploadedFile.getPath());
            file.write(uploadedFile.getPathWithFileName());
            hash = getFileHash(uploadedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }

    @Transactional
    private cz.uhk.chemdb.model.chemdb.table.UploadedFile createUploadedFile(UploadedFile file) {
        cz.uhk.chemdb.model.chemdb.table.UploadedFile uploadedFile = new cz.uhk.chemdb.model.chemdb.table.UploadedFile();
        uploadedFile.setFileName(file.getFileName());
        uploadedFile.setFileSize(file.getSize());
        uploadedFile.setTimestamp(LocalDateTime.now());
        String dir = FILE_UPLOAD_PATH + uploadedFile.getTimestamp().format(dateFormats.getFitbitDateFormat());
        uploadedFile.setPath(dir);
        uploadedFile.setUser(userManager.getCurrentUser());
        uploadedFile.setContentType(file.getContentType());
        uploadedFileRepository.save(uploadedFile);
        return uploadedFile;
    }

    public String getFileHash(cz.uhk.chemdb.model.chemdb.table.UploadedFile uploadedFile) {
        return uploadedFile.getUuid();
    }
}
