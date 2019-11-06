package cz.uhk.chemdb.model.chemdb.table;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;

@Entity
@Table(name = "uploaded_files")
public class UploadedFile extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String fileName;
    String path;
    String contentType;
    long fileSize;
    @ManyToOne
    private User user;
    private LocalDateTime timestamp;

    public UploadedFile() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathWithFileName() {
        return path + File.separator + uuid;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * THX https://stackoverflow.com/questions/3758606/how-to-convert-byte-size-into-human-readable-format-in-java/3758880#3758880
     *
     * @return
     */
    public String getFormatedFileSize(boolean si) {
        int unit = si ? 1000 : 1024;
        if (fileSize < unit) return fileSize + " B";
        int exp = (int) (Math.log(fileSize) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", fileSize / Math.pow(unit, exp), pre);
    }

    @Override
    public String toString() {
        return id + " - " + fileName + " (" + getFormatedFileSize(true) + ")";
    }
}
