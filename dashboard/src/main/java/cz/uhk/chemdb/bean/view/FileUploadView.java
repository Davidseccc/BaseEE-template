package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.util.DialogUtils;
import cz.uhk.chemdb.util.FileUploadUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class FileUploadView {

    private UploadedFile file;
    @Inject
    FileUploadUtils fileUploadUtils;

    String selectedUploadType;


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
        System.out.println("handleFileUpload()");
        file = event.getFile();
        fileUploadUtils.saveUploadedFile(event.getFile(), event.getFile().getFileName());
        openDialog();
        //FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        //FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void openDialog() {
        System.out.println("openDialog()");
        DialogUtils.openPageAsDialog("dialog/importDataDialog");
    }

    public List<String> getUploadTypes() {
        List<String> uplaodTypes = new ArrayList<>();
        uplaodTypes.add("Invitro data");
        uplaodTypes.add("K databáze");
        uplaodTypes.add("Typ3");
        uplaodTypes.add("Typ4");
        uplaodTypes.add("Typ5");
        return uplaodTypes;
    }

    public String getSelectedUploadType() {
        return selectedUploadType;
    }

    public void setSelectedUploadType(String selectedUploadType) {
        this.selectedUploadType = selectedUploadType;
    }
}
