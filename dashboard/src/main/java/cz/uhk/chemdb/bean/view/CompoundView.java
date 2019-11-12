package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.bean.CompoundSelector;
import cz.uhk.chemdb.model.chemdb.repositories.CompoundRepository;
import cz.uhk.chemdb.model.chemdb.table.Compound;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Named
@RequestScoped
public class CompoundView {
    @Inject
    private CompoundRepository compoundRepository;
    private List<Compound> compounds;
    private List<Compound> filteredCompounds;
    @Inject
    private CompoundSelector compoundSelector;
    @Inject
    private FullTextSearch fullTextSearch;

    @PostConstruct
    public void init() {
        compounds = compoundRepository.findByDeletedAtIsNullOrderByIdAsc();
    }

    public void search() {
        compounds = compoundRepository.fullTextSearch("%" + fullTextSearch.getSearchString() + "%");
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

    public void redirect(Compound compound) {
        compoundSelector.setSelectedCompound(compound);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("compound.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void redirect(long id) {
        System.out.println(id);
        Compound compound = compoundRepository.findBy(id);
        compoundSelector.setSelectedCompound(compound);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("compound.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
