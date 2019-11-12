package cz.uhk.chemdb.bean.view.dialog;


import cz.uhk.chemdb.bean.UserManager;
import cz.uhk.chemdb.model.chemdb.repositories.OrganismRepository;
import cz.uhk.chemdb.model.chemdb.table.EventType;
import cz.uhk.chemdb.model.chemdb.table.LogSection;
import cz.uhk.chemdb.model.chemdb.table.Organism;
import cz.uhk.chemdb.util.DialogUtils;
import cz.uhk.chemdb.util.LogUtils;
import cz.uhk.chemdb.utils.StringUtils;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;

@Named
@ViewScoped
public class EditOrganismDialog implements Serializable {


    @Inject
    private OrganismRepository organismRepository;
    @Inject
    private LogUtils logUtils;
    @Inject
    private UserManager userManager;

    private String originalOrganismName;
    private Organism organism;
    private boolean newOrganism;

    public void openDialog() {
        DialogUtils.openPageAsDialog("dialog/editOrganism");
    }

    public void onRowEdit(Organism organismToEdit) {
        DialogUtils.openPageAsDialog("dialog/editOrganism", organismToEdit.getId());
    }

    @PostConstruct
    public void init() {
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (id != null) {
            organism = organismRepository.findBy(Long.parseLong(id));
            originalOrganismName = organism.getName();
        } else {
            organism = new Organism();
            newOrganism = true;
        }
    }

    public boolean isNewOrganism() {
        return newOrganism;
    }

    @Transactional
    public void saveOrganismAndClose() {
        if (newOrganism && StringUtils.isEmpty(organism.getName())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid name", "Name could not be empty");
            PrimeFaces.current().dialog().showMessageDynamic(message);
            return;
        }

        if (newOrganism && existOwnerWithName(organism.getName())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Name already exists", "Organism with same name already exist");
            PrimeFaces.current().dialog().showMessageDynamic(message);
            return;
        }

        organismRepository.save(organism);
        logUtils.createAndSaveLog(newOrganism ? EventType.CREATE : EventType.EDIT, userManager.getCurrentUser(), LogSection.ORGANISM, newOrganism ? organism.getName() : String.format("%s changed into %s", originalOrganismName, organism.getName()));
        close();
    }

    private boolean existOwnerWithName(String name) {
        return organismRepository.findOptionalByName(name) != null;
    }

    public void close() {
        DialogUtils.closeDialog();
    }

    public Organism getOrganism() {
        return organism;
    }

    public void setOrganism(Organism organism) {
        this.organism = organism;
    }
}