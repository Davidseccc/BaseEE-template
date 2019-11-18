package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.bean.CompoundSelector;
import cz.uhk.chemdb.model.chemdb.repositories.CompoundRepository;
import cz.uhk.chemdb.model.chemdb.repositories.DescriptorRepository;
import cz.uhk.chemdb.model.chemdb.table.Compound;
import cz.uhk.chemdb.utils.ObprpService;

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
    @Inject
    CompoundRepository compoundRepository;
    @Inject
    DescriptorRepository descriptorRepository;
    @Inject
    ObprpService obPropService;
    private Compound compound;

    @PostConstruct
    public void init() {
        if (compoundSelector != null && compoundSelector.getSelectedCompound() != null) {
            compound = compoundRepository.findBy(compoundSelector.getSelectedCompound().getId());
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
