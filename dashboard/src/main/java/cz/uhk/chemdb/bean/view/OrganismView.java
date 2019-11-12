package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.model.chemdb.repositories.OrganismRepository;
import cz.uhk.chemdb.model.chemdb.table.Organism;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Locale;

@Named
@RequestScoped
public class OrganismView {
    @Inject
    private OrganismRepository organismRepository;
    private List<Organism> organisms;
    private List<Organism> filteredOrganisms;
    private String newOrganismName;

    @Inject
    private FullTextSearch fullTextSearch;

    @PostConstruct
    public void init() {
        organisms = organismRepository.findAll();
        //organisms = organismRepository.findByDeletedAtIsNullOrderByIdAsc();
    }

    public void search() {
        System.out.println("Search " + fullTextSearch.getSearchString());
    }

    public boolean filterByMw(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        return ((Comparable) value).compareTo(Float.valueOf(filterText)) > 0;
    }

    public void delete(Organism organism) {
        //todo: implement delete task;
    }

    public List<Organism> getOrganisms() {
        return organisms;
    }

    public void setOrganisms(List<Organism> organisms) {
        this.organisms = organisms;
    }

    public List<Organism> getFilteredOrganisms() {
        return filteredOrganisms;
    }

    public void setFilteredOrganisms(List<Organism> filteredOrganisms) {
        this.filteredOrganisms = filteredOrganisms;
    }

    public String getNewOrganismName() {
        return newOrganismName;
    }

    public void setNewOrganismName(String newOrganismName) {
        this.newOrganismName = newOrganismName;
    }
}
