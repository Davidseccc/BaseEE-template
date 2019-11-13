package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.bean.UserManager;
import cz.uhk.chemdb.model.chemdb.parser.KDataExcelParser;
import cz.uhk.chemdb.model.chemdb.repositories.CompoundRepository;
import cz.uhk.chemdb.model.chemdb.repositories.OwnerRepositiry;
import cz.uhk.chemdb.model.chemdb.repositories.UploadedFileRepository;
import cz.uhk.chemdb.model.chemdb.table.*;
import cz.uhk.chemdb.util.DialogUtils;
import cz.uhk.chemdb.util.LogUtils;
import cz.uhk.chemdb.utils.ObprpService;
import cz.uhk.chemdb.utils.StringUtils;
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
public class ImportKDataDialogView implements Serializable {

    @Inject
    private OwnerRepositiry ownerRepositiry;
    @Inject
    private LogUtils logUtils;
    @Inject
    private UserManager userManager;
    @Inject
    private ObprpService obprpService;
    @Inject
    private CompoundRepository compoundRepository;
    @Inject
    private UploadedFileRepository uploadedFileRepository;

    List<KDataExcelParser.KDatabaseDTO> kDatabaseDTOS;

    List<String> owners;

    @PostConstruct
    public void init() {
        String fileHash = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("fileHash");
        owners = new ArrayList<>();
        for (Owner owner : ownerRepositiry.findAll()) {
            owners.add(owner.getName());
        }
        UploadedFile uploadedFile = uploadedFileRepository.findOptionalByUuid(fileHash);
        if (uploadedFile != null) {
            kDatabaseDTOS = processKDatabase(uploadedFile.getPathWithFileName());
        }

    }

    private List<KDataExcelParser.KDatabaseDTO> processKDatabase(String filePath) {
        KDataExcelParser excelParser = new KDataExcelParser();
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
        System.out.println("saveAndClose()");
        for (KDataExcelParser.KDatabaseDTO kDatabaseDTO : kDatabaseDTOS) {
            saveDTOToCompound(kDatabaseDTO);
        }
        DialogUtils.closeDialog();
    }

    public void onAddNew() {
        KDataExcelParser.KDatabaseDTO kDatabaseDTO = new KDataExcelParser.KDatabaseDTO();
        kDatabaseDTOS.add(kDatabaseDTO);
        FacesMessage msg = new FacesMessage("New Row added", kDatabaseDTO.getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowRemove(KDataExcelParser.KDatabaseDTO kDatabaseDTO) {
        kDatabaseDTOS.remove(kDatabaseDTO);
    }

    public void close() {
        DialogUtils.closeDialog();
        FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts().add("stop()");
    }

    @Transactional
    private void saveDTOToCompound(KDataExcelParser.KDatabaseDTO kDatabaseDTO) {
        Owner owner = ownerRepositiry.findOptionalByName(kDatabaseDTO.getOwner());
        if (owner != null) {
            Compound compound = new Compound();
            compound.setK(Integer.parseInt(
                    kDatabaseDTO.getId().substring(kDatabaseDTO.getId().indexOf("K") + 1)));
            compound.setSmiles(kDatabaseDTO.getSmiles());
            ObprpService.ObPropResult result = obprpService.call(compound.getSmiles());
            Descriptor descriptor = new Descriptor();
            compound.setMw(result != null ? (result.getMol_weight()) : Float.NaN);
            compound.setOriginalCodename(kDatabaseDTO.getOriginalCodename());
            compound.setOwner(owner);
            compound.setMeltingPoint(new MeltingPoint(kDatabaseDTO.getMeltingPoint(), compound));
            descriptor.setHRMS(kDatabaseDTO.getHRMS());
            descriptor.setNMR(kDatabaseDTO.getNMR());
            descriptor.setHRMS(kDatabaseDTO.getHRMS());
            descriptor.setAtoms(result != null ? result.getNum_atoms() : Integer.MIN_VALUE);
            descriptor.setCompound(compound);

            if (!StringUtils.isEmpty(kDatabaseDTO.getPurity())) {
                if (kDatabaseDTO.getPurity().startsWith(">") || (kDatabaseDTO.getPurity().startsWith("<"))) {
                    descriptor.setPurityOperator(kDatabaseDTO.getPurity().charAt(0));
                    descriptor.setPurity(Float.parseFloat(kDatabaseDTO.getPurity().substring(1).replaceAll(",", ".")));
                } else {
                    descriptor.setPurityOperator(' ');
                    descriptor.setPurity(Integer.parseInt(kDatabaseDTO.getPurity()));
                }
            }
            descriptor.setSolubility(kDatabaseDTO.getSolubility());
            compound.setDescriptor(descriptor);
            compoundRepository.save(compound);
            logUtils.createAndSaveLog(EventType.CREATE, userManager.getCurrentUser(), LogSection.COMPOUND, compound.toString());
        }
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
