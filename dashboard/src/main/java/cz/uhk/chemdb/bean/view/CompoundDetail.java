package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.bean.CompoundSelector;
import cz.uhk.chemdb.model.chemdb.table.Compound;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@RequestScoped
@Named
public class CompoundDetail {

    @Inject
    CompoundSelector compoundSelector;
    private Compound compound;

    @PostConstruct
    public void init() {
        if (compoundSelector != null && compoundSelector.getSelectedCompound() != null) {
            compound = compoundSelector.getSelectedCompound();
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(CompoundSelector.NO_COMP_SELECTED);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public Compound getCompound() {
        return compound;
    }

    public void setCompound(Compound compound) {
        this.compound = compound;
    }
}
