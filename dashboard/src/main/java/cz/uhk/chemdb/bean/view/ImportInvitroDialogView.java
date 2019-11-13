package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.bean.UserManager;
import cz.uhk.chemdb.model.chemdb.parser.InvitroExcelParser;
import cz.uhk.chemdb.model.chemdb.parser.KDataExcelParser;
import cz.uhk.chemdb.model.chemdb.repositories.*;
import cz.uhk.chemdb.model.chemdb.table.*;
import cz.uhk.chemdb.util.DialogUtils;
import cz.uhk.chemdb.util.LogUtils;
import org.primefaces.event.CellEditEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class ImportInvitroDialogView implements Serializable {

    List<String> errors;
    String selectedError;
    @Inject
    private OwnerRepositiry ownerRepositiry;
    @Inject
    private LogUtils logUtils;
    @Inject
    private UserManager userManager;
    @Inject
    private InvitroRepository invitroRepository;
    @Inject
    private CompoundRepository compoundRepository;


    List<KDataExcelParser.KDatabaseDTO> kDatabaseDTOS;
    InvitroExcelParser.Invitro invitro;
    @Inject
    private OrganismRepository organismRepository;
    @Inject
    private TargetRepository targetRepository;
    @Inject
    private UploadedFileRepository uploadedFileRepository;

    @PostConstruct
    public void init() {
        kDatabaseDTOS = new ArrayList<>();
        String fileHash = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("fileHash");
        UploadedFile uploadedFile = uploadedFileRepository.findOptionalByUuid(fileHash);

        if (uploadedFile != null) {
            invitro = processInvitroDatabase(uploadedFile.getPathWithFileName());
        }
    }

    private InvitroExcelParser.Invitro processInvitroDatabase(String filePath) {
        InvitroExcelParser excelParser = new InvitroExcelParser();
        try {
            return excelParser.parse(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            logUtils.createAndSaveLog(EventType.UPLOAD_DOCUMENT, userManager.getCurrentUser(),
                    LogSection.COMPOUND, "Cell changed old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    @Transactional
    public void saveAndClose() {
        saveInvitro();
        close();
    }

    public void close() {
        DialogUtils.closeDialog();
    }


    public void saveInvitro() {

        for (InvitroExcelParser.Data data : invitro.getInvitoroData()) {
            Invitro invitro = new Invitro();
            int k = Integer.parseInt(data.getId().substring(1));
            invitro.setCompound(compoundRepository.findByK(k));
            invitro.setOrganism(organismRepository.findOptionalByName(this.invitro.getOrganism()));
            Target target = targetRepository.findOptionalByName(this.invitro.getTargetEnum());
            invitro.setTarget(target);
            if (data.getValue().startsWith(">") || data.getValue().startsWith("<")) {
                invitro.setValueoperator(data.getValue().charAt(0));
                invitro.setValue(Double.parseDouble(data.getValue().substring(1).replaceAll(",", ".")));
            } else {
                invitro.setValueoperator(' ');
                invitro.setValue(Double.parseDouble(data.getValue().replaceAll(",", ".")));
            }
            invitro.setErrorType(ErrorType.findByName(data.getError()));
            invitro.setValue_text(data.getValueError());
            invitroRepository.save(invitro);
        }
    }

    public void onAddNew() {
        InvitroExcelParser.Data data = new InvitroExcelParser.Data();
        invitro.getInvitoroData().add(data);
        FacesMessage msg = new FacesMessage("New Row added", data.getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowRemove(InvitroExcelParser.Data invitroData) {
        invitro.getInvitoroData().remove(invitroData);
    }


    public InvitroExcelParser.Invitro getInvitro() {
        return invitro;
    }

    public void setInvitro(InvitroExcelParser.Invitro invitro) {
        this.invitro = invitro;
    }

    public List<String> getErrors() {
        List<String> errorTypes = new ArrayList<>();
        for (ErrorType errorType : ErrorType.values()) {
            errorTypes.add(errorType.getName());
        }
        return errorTypes;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getSelectedError() {
        return selectedError;
    }

    public void setSelectedError(String selectedError) {
        this.selectedError = selectedError;
    }
}
