package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.bean.CompoundSelector;
import cz.uhk.chemdb.model.chemdb.repositories.CompoundRepository;
import cz.uhk.chemdb.model.chemdb.table.Compound;
import cz.uhk.chemdb.model.chemdb.table.MeltingPoint;
import cz.uhk.chemdb.utils.StringUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    public boolean gtOrEqual(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        if (value == null) {
            return false;
        }
        if (value instanceof Double) {
            return ((Comparable) value).compareTo(Double.valueOf(filterText)) > 0;
        } else if (value instanceof Float) {
            return ((Comparable) value).compareTo(Float.valueOf(filterText)) > 0;
        } else if (value instanceof Integer) {
            return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
        } else if (value instanceof Long) {
            return ((Comparable) value).compareTo(Long.valueOf(filterText)) > 0;
        }
        return false;
    }

    public boolean filter(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }
        if (value instanceof MeltingPoint) {
            if (filterText.equalsIgnoreCase("oil")) {
                return ((MeltingPoint) value).isOil() == true;
            }
            if (filterText.equalsIgnoreCase(value.toString())) {
                return true;
            } else if (filterText.contains("-")) {
                String from = filterText.substring(0, filterText.indexOf("-"));
                String to = filterText.substring(filterText.indexOf("-"));
                if (StringUtils.isNumeric(from) && StringUtils.isNumeric(to)) {
                    int fromVal = Math.round(((MeltingPoint) value).getTemperatureFrom());
                    int toVal = Math.round(((MeltingPoint) value).getTemperatureTo());
                    int fromFilter = StringUtils.getNumber(from, Integer.class).intValue();
                    int toFilter = StringUtils.getNumber(to, Integer.class).intValue();
                    return ((fromVal == fromFilter) && (toVal == toFilter));
                }

            } else if (StringUtils.isNumeric(filterText)) {
                int filteredValue = StringUtils.getNumber(filterText, Integer.class).intValue();
                float from = (((MeltingPoint) value).getTemperatureFrom() == null) ? Float.NaN : ((MeltingPoint) value).getTemperatureFrom();
                float to = (((MeltingPoint) value).getTemperatureTo() == null) ? Float.NaN : (((MeltingPoint) value).getTemperatureTo());
                return (Math.round(from) == filteredValue || Math.round(to) == filteredValue);
            }
        }
        return false;
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

    public String encode(String smile, String charset) throws UnsupportedEncodingException {
        return URLEncoder.encode(smile, charset);
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
