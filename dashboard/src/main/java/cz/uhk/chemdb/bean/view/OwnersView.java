package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.bean.CompoundSelector;
import cz.uhk.chemdb.model.chemdb.repositories.OwnerRepositiry;
import cz.uhk.chemdb.model.chemdb.table.Owner;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class OwnersView {
    @Inject
    private OwnerRepositiry ownerRepositiry;
    private List<Owner> owners;
    private List<Owner> filteredOwners;
    @Inject
    private CompoundSelector compoundSelector;
    @Inject
    private FullTextSearch fullTextSearch;

    @PostConstruct
    public void init() {
        owners = ownerRepositiry.findAll();
        //organisms = organismRepository.findByDeletedAtIsNullOrderByIdAsc();
    }

    public void search() {
        System.out.println("Search " + fullTextSearch.getSearchString());
    }

    public void delete(Owner owner) {
        //todo: implement delete task;
    }


    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }

    public List<Owner> getFilteredOwners() {
        return filteredOwners;
    }

    public void setFilteredOwners(List<Owner> filteredOwners) {
        this.filteredOwners = filteredOwners;
    }
}
