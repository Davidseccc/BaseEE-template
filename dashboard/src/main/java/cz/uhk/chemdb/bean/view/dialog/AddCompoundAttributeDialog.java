package cz.uhk.chemdb.bean.view.dialog;

import cz.uhk.chemdb.model.chemdb.repositories.CompoundRepository;
import cz.uhk.chemdb.model.chemdb.table.Attribute;
import cz.uhk.chemdb.model.chemdb.table.Compound;
import cz.uhk.chemdb.util.DialogUtils;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Set;

@Named
@ViewScoped
public class AddCompoundAttributeDialog implements Serializable {

    @Inject
    CompoundRepository compoundRepository;
    private Compound compound;
    private Set<Attribute> attributes;
    private Attribute attribute;

    public void openDialog(Compound compound) {
        if (compound != null) {
            System.out.println(compound.toString());
            DialogUtils.openPageAsDialog("dialog/addCompoundAttribute");
            this.compound = compound;
            attributes = compound.getAttributes();
            attribute = new Attribute();
        }
    }

    @Transactional
    public void saveAndClose() {
        System.out.println(attribute.toString());
        compound.getAttributes().add(attribute);
        compoundRepository.save(compound);
        close();
    }

    public void close() {
        DialogUtils.closeDialog();
    }

    public Compound getCompound() {
        return compound;
    }

    public void setCompound(Compound compound) {
        this.compound = compound;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
}
