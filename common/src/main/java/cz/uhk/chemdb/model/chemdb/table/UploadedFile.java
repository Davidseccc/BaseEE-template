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
        return path + File.separator + fileName;
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

    @Override
    public String toString() {
        return id + " - " + fileName + "(" + path + ")";
    }
}
