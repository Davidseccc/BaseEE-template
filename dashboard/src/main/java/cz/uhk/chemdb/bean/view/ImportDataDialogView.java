package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.bean.UserManager;
import cz.uhk.chemdb.model.chemdb.repositories.OwnerRepositiry;
import cz.uhk.chemdb.model.chemdb.table.EventType;
import cz.uhk.chemdb.model.chemdb.table.LogSection;
import cz.uhk.chemdb.model.chemdb.table.Owner;
import cz.uhk.chemdb.util.DialogUtils;
import cz.uhk.chemdb.util.KDataExcelParser;
import cz.uhk.chemdb.util.LogUtils;
import org.primefaces.event.CellEditEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class ImportDataDialogView implements Serializable {

    @Inject
    OwnerRepositiry ownerRepositiry;
    @Inject
    LogUtils logUtils;
    @Inject
    UserManager userManager;
    @Inject
    FileUploadView fileUploadView;

    List<KDataExcelParser.KDatabaseDTO> kDatabaseDTOS;
    List<String> owners;

    @PostConstruct
    public void init() {
        kDatabaseDTOS = new ArrayList<>();
        owners = new ArrayList<>();
        for (Owner owner : ownerRepositiry.findAll()) {
            owners.add(owner.getName());
        }

    }

    public void openDialog(String filePath, String uploadType) {
        System.out.println("filePath" + filePath);
        switch (uploadType) {
            case "K databáze":
                kDatabaseDTOS = processKDatabase(filePath);
                break;
        }
        DialogUtils.openPageAsDialog("dialog/importDataDialog");
    }

    private List<KDataExcelParser.KDatabaseDTO> processKDatabase(String filePath) {
        KDataExcelParser excelParser = new KDataExcelParser();
        excelParser.setSAMPLE_XLSX_FILE_PATH(filePath);
        try {
            return excelParser.parse();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            logUtils.createAndSaveLog(EventType.UPLOAD_DOCUMENT, userManager.getCurrentUser(), LogSection.COMPOUND, "Cell changed old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void saveAndClose() {

    }

    public List<KDataExcelParser.KDatabaseDTO> getkDatabaseDTOS() {
        return kDatabaseDTOS;
    }

    public void setkDatabaseDTOS(List<KDataExcelParser.KDatabaseDTO> kDatabaseDTOS) {
        this.kDatabaseDTOS = kDatabaseDTOS;
    }

    public List<String> getOwners() {
        return owners;
    }

    public void setOwners(List<String> owners) {
        this.owners = owners;
    }
}
