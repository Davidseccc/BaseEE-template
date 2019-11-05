package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.model.chemdb.table.FileUploadType;
import cz.uhk.chemdb.util.FileUploadUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class FileUploadView {

    private UploadedFile file;
    @Inject
    FileUploadUtils fileUploadUtils;

    FileUploadType selectedUploadType;

    String fileHash;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() {
        if (file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        fileHash = fileUploadUtils.saveUploadedFile(event.getFile());
    }

    public List<FileUploadType> getUploadTypes() {
        return Arrays.stream(FileUploadType.values()).collect(Collectors.toList());
    }

    public FileUploadType getSelectedUploadType() {
        return selectedUploadType;
    }

    public void setSelectedUploadType(FileUploadType selectedUploadType) {
        this.selectedUploadType = selectedUploadType;
    }

    public FileUploadUtils getFileUploadUtils() {
        return fileUploadUtils;
    }

    public void setFileUploadUtils(FileUploadUtils fileUploadUtils) {
        this.fileUploadUtils = fileUploadUtils;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }
}
