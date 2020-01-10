package cz.uhk.chemdb.util.export;

import cz.uhk.chemdb.bean.CompoundDTO;
import cz.uhk.chemdb.utils.CSVExportFormats;
import cz.uhk.chemdb.utils.OpenBabelUtils;
import cz.uhk.chemdb.utils.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.ejb.Singleton;
import java.io.*;
import java.util.Calendar;
import java.util.Date;


@Singleton
public class ExcelExport {

    public StreamedContent startExport(CompoundDTO compound) {
        StreamedContent exportFile = null;
        try {
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet(String.format("K_%s", compound.getCompounds().get("k")));
            Writer writer = new Writer(sheet, 1, 0);
            if (compound.getCompounds().get("smiles") != null) {
                addSmile(compound.getCompounds().get("smiles"), wb, sheet, 1, 1);
                writer.incrementRow(19);
            }
            int rowcount = 16;
            Row rr = sheet.createRow(++rowcount);
            Cell k = rr.createCell(0);
            k.setCellValue(String.format("K_%s", compound.getCompounds().get("k")));
            for (String key : compound.getCompounds().keySet()) {
                Row row = sheet.createRow(++rowcount);
                Cell cellKey = row.createCell(0);
                Cell cellValue = row.createCell(1);
                cellKey.setCellValue(key);
                cellValue.setCellValue(compound.compounds.get(key));
            }

            Row r = sheet.createRow(++rowcount);
            Cell name2 = r.createCell(0);
            name2.setCellValue("Targets:");
            for (String key : compound.getTargets().keySet()) {
                Row row = sheet.createRow(++rowcount);
                int cell = 0;
                Cell cellKey = row.createCell(++cell);
                cellKey.setCellValue(key);
                int columnCount = 2;
                for (String targetName : compound.targets.get(key).keySet()) {
                    Cell cellKey1 = row.createCell(++columnCount);
                    cellKey1.setCellValue(targetName);
                    Cell cellValue1 = row.createCell(++columnCount);
                    cellValue1.setCellValue(compound.targets.get(key).get(targetName));
                }

            }

            Row row2 = sheet.createRow(++rowcount);
            Cell name3 = row2.createCell(0);
            name3.setCellValue("Attributes:");
            for (String key : compound.getAttributes().keySet()) {
                Row row = sheet.createRow(++rowcount);
                Cell cellKey = row.createCell(1);
                Cell cellValue = row.createCell(2);
                cellKey.setCellValue(key);
                cellValue.setCellValue(compound.attributes.get(key));
            }

            //Write the Excel file
            File file = File.createTempFile(CSVExportFormats.TEMP_FILE_PREFIX, Long.toString(System.currentTimeMillis()));
            FileOutputStream fileOut = new FileOutputStream(file);
            wb.write(fileOut);
            fileOut.close();
            InputStream stream;
            stream = new FileInputStream(file);
            exportFile = new DefaultStreamedContent(stream, "text/csv", String.format("K_%s.xlsx", compound.getCompounds().get("k")));

        } catch (Exception e) {
            System.out.println(e);
        }
        return exportFile;
    }

    private void addSmile(String smiles, Workbook wb, Sheet sheet, int col, int row) throws IOException {
        InputStream inputStream = OpenBabelUtils.getFile(smiles);
        byte[] bytes = IOUtils.toByteArray(inputStream);
        int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
        inputStream.close();
        CreationHelper helper = wb.getCreationHelper();
        Drawing drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setCol1(col);
        anchor.setRow1(row);
        Picture pict = drawing.createPicture(anchor, pictureIdx);
        pict.resize();
    }

    public class Writer {
        Sheet sheet;
        int row;
        int cell;

        public Writer(Sheet sheet, int start_row, int start_cell) {
            this.sheet = sheet;
            this.row = start_row;
            this.cell = start_cell;
        }

        public Writer incrementCell() {
            this.cell++;
            return this;
        }

        public Writer incrementCell(int offset) {
            this.cell += offset;
            return this;
        }

        public Writer decrementCell(int offset) {
            this.cell -= offset;
            return this;
        }


        public Writer incrementRow() {
            this.row++;
            return this;
        }

        public Writer incrementRow(int offset) {
            this.row += offset;
            return this;
        }

        public Writer write(String string) {
            Row row = sheet.createRow(this.row);
            Cell cell = row.createCell(this.cell);
            cell.setCellValue(StringUtils.isEmpty(string) ? "" : string);
            return this;
        }

        public Writer write(double val) {
            Row row = sheet.createRow(this.row);
            Cell cell = row.createCell(this.cell);
            cell.setCellValue(val);
            return this;
        }

        public Writer write(Date date) {
            Row row = sheet.createRow(this.row);
            Cell cell = row.createCell(this.cell);
            cell.setCellValue(date);
            return this;
        }

        public Writer write(Calendar calendar) {
            Row row = sheet.createRow(this.row);
            Cell cell = row.createCell(this.cell);
            cell.setCellValue(calendar);
            return this;
        }

    }

}
