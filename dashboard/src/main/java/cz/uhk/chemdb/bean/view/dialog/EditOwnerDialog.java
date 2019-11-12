package cz.uhk.chemdb.bean.view.dialog;


import cz.uhk.chemdb.bean.UserManager;
import cz.uhk.chemdb.model.chemdb.repositories.OwnerRepositiry;
import cz.uhk.chemdb.model.chemdb.table.EventType;
import cz.uhk.chemdb.model.chemdb.table.LogSection;
import cz.uhk.chemdb.model.chemdb.table.Owner;
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
public class EditOwnerDialog implements Serializable {


    @Inject
    OwnerRepositiry ownerRepositiry;
    @Inject
    UserManager userManager;
    @Inject
    LogUtils logUtils;

    private String originalOwnerName;
    private Owner owner;
    private boolean newOwner;

    public void openDialog() {
        DialogUtils.openPageAsDialog("dialog/editOwner");
    }

    public void onRowEdit(Owner owner) {
        DialogUtils.openPageAsDialog("dialog/editOwner", owner.getId());
    }

    @PostConstruct
    public void init() {
        String id = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
        if (id != null) {
            owner = ownerRepositiry.findBy(Long.parseLong(id));
            originalOwnerName = owner.getName();
        } else {
            owner = new Owner();
            newOwner = true;
        }
    }

    public boolean isNewOwner() {
        return newOwner;
    }

    @Transactional
    public void saveOwnerAndClose() {
        if (newOwner && StringUtils.isEmpty(owner.getName())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid name", "Name could not be empty");
            PrimeFaces.current().dialog().showMessageDynamic(message);
            return;
        }

        if (newOwner && existOwnerWithName(owner.getName())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Name already exists", "Owner with same name already exist");
            PrimeFaces.current().dialog().showMessageDynamic(message);
            return;
        }
        ownerRepositiry.save(owner);
        logUtils.createAndSaveLog(newOwner ? EventType.CREATE : EventType.EDIT, userManager.getCurrentUser(), LogSection.ORGANISM, newOwner ? owner.getName() : String.format("%s changed into %s", originalOwnerName, owner.getName()));
        close();
    }

    private boolean existOwnerWithName(String name) {
        return ownerRepositiry.findOptionalByName(name) != null;
    }

    public void close() {
        DialogUtils.closeDialog();
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}