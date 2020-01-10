package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.model.chemdb.repositories.UploadedFileRepository;
import cz.uhk.chemdb.model.chemdb.table.FileUploadType;
import cz.uhk.chemdb.util.FileUploadUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class FileUploadView implements Serializable {

    private UploadedFile file;
    @Inject
    private FileUploadUtils fileUploadUtils;
    @Inject
    private UploadedFileRepository uploadedFileRepository;

    private FileUploadType selectedUploadType;

    private String fileHash;

    private StreamedContent fileToDownload;


    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() {
        if (file != null) {
            fileHash = fileUploadUtils.saveUploadedFile(file);
        }
    }

    public StreamedContent downloadFile(String fileHash) {
        cz.uhk.chemdb.model.chemdb.table.UploadedFile fileToView = uploadedFileRepository.findOptionalByUuid(fileHash);
        if (fileToView != null) {
            InputStream stream = null;
            try {
                stream = new FileInputStream(new File(fileToView.getPathWithFileName()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            fileToDownload = new DefaultStreamedContent(stream, fileToView.getContentType(), fileToView.getFileName());
        }
        return fileToDownload;
    }

    public String getUploadedFileStringInfo(String fileHash) {
        cz.uhk.chemdb.model.chemdb.table.UploadedFile fileToView = uploadedFileRepository.findOptionalByUuid(fileHash);
        if (fileToView != null) {
            return fileToView.toString();
        }
        return "No information available";

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

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }
}
