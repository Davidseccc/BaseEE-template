package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.model.chemdb.repositories.TargetRepository;
import cz.uhk.chemdb.model.chemdb.table.Owner;
import cz.uhk.chemdb.model.chemdb.table.Target;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class TargetsView {
    @Inject
    private TargetRepository ownerRepositiry;
    private List<Target> targets;
    private List<Target> filteredTargets;
    @Inject
    private FullTextSearch fullTextSearch;

    @PostConstruct
    public void init() {
        targets = ownerRepositiry.findAll();
        //organisms = organismRepository.findByDeletedAtIsNullOrderByIdAsc();
    }

    public void search() {
        System.out.println("Search " + fullTextSearch.getSearchString());
    }

    public void delete(Owner owner) {
        //todo: implement delete task;
    }

    public List<Target> getTargets() {
        return targets;
    }

    public void setTargets(List<Target> targets) {
        this.targets = targets;
    }

    public List<Target> getFilteredTargets() {
        return filteredTargets;
    }

    public void setFilteredTargets(List<Target> filteredTargets) {
        this.filteredTargets = filteredTargets;
    }
}
