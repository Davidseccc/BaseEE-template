package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.model.chemdb.repositories.CompoundRepository;
import cz.uhk.chemdb.model.chemdb.table.Compound;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class LeftMenuView {
    @Inject
    CompoundRepository compoundRepository;
    private List<Compound> compounds;
    private List<Compound> filteredCompounds;

    @PostConstruct
    public void init() {
        compounds = compoundRepository.findAll();
    }

    public List<Compound> getCompounds() {
        return compounds;
    }

    public void setCompounds(List<Compound> compounds) {
        this.compounds = compounds;
    }

    public List<Compound> getFilteredCompounds() {
        return filteredCompounds;
    }

    public void setFilteredCompounds(List<Compound> filteredCompounds) {
        this.filteredCompounds = filteredCompounds;
    }
}
