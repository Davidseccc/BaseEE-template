package cz.uhk.chemdb.bean.view.dialog;


import cz.uhk.chemdb.bean.UserManager;
import cz.uhk.chemdb.model.chemdb.repositories.TargetRepository;
import cz.uhk.chemdb.model.chemdb.table.EventType;
import cz.uhk.chemdb.model.chemdb.table.LogSection;
import cz.uhk.chemdb.model.chemdb.table.Owner;
import cz.uhk.chemdb.model.chemdb.table.Target;
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
public class EditTargetDialog implements Serializable {


    @Inject
    TargetRepository targetRepository;
    @Inject
    UserManager userManager;
    @Inject
    LogUtils logUtils;

    private String originalOwnerName;
    private Target target;
    private boolean newTarget;

    public void openDialog() {
        DialogUtils.openPageAsDialog("dialog/editTarget");
    }

    public void onRowEdit(Owner owner) {
        DialogUtils.openPageAsDialog("dialog/editTarget", owner.getId());
    }

    @PostConstruct
    public void init() {
        String id = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
        if (id != null) {
            target = targetRepository.findBy(Long.parseLong(id));
            originalOwnerName = target.getName();
        } else {
            target = new Target();
            newTarget = true;
        }
    }

    public boolean isNewTarget() {
        return newTarget;
    }

    @Transactional
    public void saveOwnerAndClose() {
        if (newTarget && StringUtils.isEmpty(target.getName())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid name", "Name could not be empty");
            PrimeFaces.current().dialog().showMessageDynamic(message);
            return;
        }

        if (newTarget && existOwnerWithName(target.getName())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Target already exists", "Target with same name already exist");
            PrimeFaces.current().dialog().showMessageDynamic(message);
            return;
        }
        targetRepository.save(target);
        logUtils.createAndSaveLog(newTarget ? EventType.CREATE : EventType.EDIT, userManager.getCurrentUser(), LogSection.TARGET, newTarget ? target.getName() : String.format("%s changed into %s", originalOwnerName, target.getName()));
        close();
    }

    private boolean existOwnerWithName(String name) {
        return targetRepository.findOptionalByName(name) != null;
    }

    public void close() {
        DialogUtils.closeDialog();
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }
}