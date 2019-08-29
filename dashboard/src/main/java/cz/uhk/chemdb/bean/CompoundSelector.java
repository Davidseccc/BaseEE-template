package cz.uhk.chemdb.bean;

import cz.uhk.chemdb.model.chemdb.table.Compound;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class CompoundSelector implements Serializable {

    public static final String NO_COMP_SELECTED = "/secured/home.xhtml";

    private Compound selectedCompound;

    public Compound getSelectedCompound() {
        return selectedCompound;
    }

    public void setSelectedCompound(Compound selectedCompound) {
        this.selectedCompound = selectedCompound;
    }
}
