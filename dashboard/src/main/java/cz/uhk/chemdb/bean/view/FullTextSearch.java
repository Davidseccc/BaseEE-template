package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.model.chemdb.repositories.CompoundRepository;
import cz.uhk.chemdb.model.chemdb.table.Compound;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class FullTextSearch {
    @Inject
    CompoundRepository compoundRepository;

    String searchString;

    public List<String> startSearch(String query) {
        List<Compound> compounds = compoundRepository.fullTextSearch("%" + query + "%");
        List<String> results = new ArrayList<>();
        for (Compound c : compounds) {
            results.add(c.toString());
        }
        return results;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
