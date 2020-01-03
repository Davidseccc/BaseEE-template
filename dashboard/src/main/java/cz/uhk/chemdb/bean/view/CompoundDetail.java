package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.bean.CompoundSelector;
import cz.uhk.chemdb.model.chemdb.repositories.CompoundRepository;
import cz.uhk.chemdb.model.chemdb.table.Compound;
import cz.uhk.chemdb.util.export.CSVExport;
import cz.uhk.chemdb.util.export.ExcelExport;
import org.primefaces.model.StreamedContent;

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
    CSVExport csvExport;
    @Inject
    ExcelExport excelExport;

    private StreamedContent exportFile;


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

    public void exportCSV() {
        System.out.println("export()");
        exportFile = CSVExport.startExport(compound);
    }

    public void exportXLS() {
        System.out.println("export()");
        exportFile = excelExport.startExport(compound);
    }


    public Compound getCompound() {
        return compound;
    }

    public void setCompound(Compound compound) {
        this.compound = compound;
    }


    public StreamedContent getExportFile() {
        return exportFile;
    }

    public void setExportFile(StreamedContent exportFile) {
        this.exportFile = exportFile;
    }
}
