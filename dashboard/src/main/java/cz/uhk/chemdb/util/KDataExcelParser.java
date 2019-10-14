package cz.uhk.chemdb.util;

import cz.uhk.chemdb.model.chemdb.repositories.OwnerRepositiry;
import org.apache.poi.ss.usermodel.*;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KDataExcelParser {
    @Inject
    OwnerRepositiry ownerRepositiry;

    private String SAMPLE_XLSX_FILE_PATH = "";


    public List<KDatabaseDTO> parse() throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));

        System.out.println("Retrieving Sheets using Java 8 forEach with lambda");
        workbook.forEach(sheet -> {
            System.out.println("=> " + sheet.getSheetName());
        });

        Sheet sheet = workbook.getSheet("List1");
        DataFormatter dataFormatter = new DataFormatter();
        List<KDatabaseDTO> kDatabaseDTOList = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getLastCellNum() > 3 && row.getLastCellNum() <= 9) {
                KDatabaseDTO kDatabaseDTO = new KDatabaseDTO();
                kDatabaseDTO.id = dataFormatter.formatCellValue(row.getCell(0));
                kDatabaseDTO.smiles = dataFormatter.formatCellValue(row.getCell(1));
                kDatabaseDTO.originalCodename = dataFormatter.formatCellValue(row.getCell(2));
                kDatabaseDTO.owner = dataFormatter.formatCellValue(row.getCell(3));
                kDatabaseDTO.meltingPoint = dataFormatter.formatCellValue(row.getCell(4));
                kDatabaseDTO.NMR = dataFormatter.formatCellValue(row.getCell(5));
                kDatabaseDTO.HRMS = dataFormatter.formatCellValue(row.getCell(6));
                kDatabaseDTO.purity = dataFormatter.formatCellValue(row.getCell(7));
                kDatabaseDTO.solubility = dataFormatter.formatCellValue(row.getCell(8));
                kDatabaseDTOList.add(kDatabaseDTO);
            }
        }
        workbook.close();
        return kDatabaseDTOList.stream().skip(1).collect(Collectors.toList());
    }

    public String getSAMPLE_XLSX_FILE_PATH() {
        return SAMPLE_XLSX_FILE_PATH;
    }

    public void setSAMPLE_XLSX_FILE_PATH(String SAMPLE_XLSX_FILE_PATH) {
        this.SAMPLE_XLSX_FILE_PATH = SAMPLE_XLSX_FILE_PATH;
    }

    public class KDatabaseDTO {
        private String id;
        private String smiles;
        private String originalCodename;
        private String owner;
        private String meltingPoint;
        private String NMR;
        private String HRMS;
        private String purity;
        private String solubility;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSmiles() {
            return smiles;
        }

        public void setSmiles(String smiles) {
            this.smiles = smiles;
        }

        public String getOriginalCodename() {
            return originalCodename;
        }

        public void setOriginalCodename(String originalCodename) {
            this.originalCodename = originalCodename;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getMeltingPoint() {
            return meltingPoint;
        }

        public void setMeltingPoint(String meltingPoint) {
            this.meltingPoint = meltingPoint;
        }

        public String getNMR() {
            return NMR;
        }

        public void setNMR(String NMR) {
            this.NMR = NMR;
        }

        public String getHRMS() {
            return HRMS;
        }

        public void setHRMS(String HRMS) {
            this.HRMS = HRMS;
        }

        public String getPurity() {
            return purity;
        }

        public void setPurity(String purity) {
            this.purity = purity;
        }

        public String getSolubility() {
            return solubility;
        }

        public void setSolubility(String solubility) {
            this.solubility = solubility;
        }

        public boolean isValidId() {
            return this.id.matches("K\\d{1,4}");
        }

        public boolean isValidsmiles() {
            return true;
        }

        public boolean isValidOriginalCodename() {
            return true;
        }

        public boolean isValidOwner() {
            return true;
        }
    }
}