package cz.uhk.chemdb.util.export;

import cz.uhk.chemdb.model.chemdb.table.Compound;
import cz.uhk.chemdb.utils.CSVExportFormats;
import cz.uhk.chemdb.utils.OpenBabelUtils;
import cz.uhk.chemdb.utils.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.ejb.Singleton;
import javax.inject.Inject;
import java.io.*;
import java.util.Calendar;
import java.util.Date;


@Singleton
public class ExcelExport {
    @Inject
    private OpenBabelUtils openBabelUtils;


    private String servletContextPath;

    public StreamedContent startExport(Compound compound) {
        StreamedContent exportFile = null;
        try {
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet(String.format("K_%s", compound.getK()));
            //FileInputStream obtains input bytes from the image file
            Writer writer = new Writer(sheet, 1, 1);
            writer.write(String.format("%s", compound.getK())).incrementRow(19);
            addSmile(compound, wb, sheet, 1, 2);
            writer.write(String.format("%s", compound.getSmiles())).incrementRow()
                    .write(String.format("%s", compound.getDoi().getDoi())).incrementRow()
                    .write(String.format("%s", compound.getIon()));


            //Write the Excel file
            File file = File.createTempFile(CSVExportFormats.TEMP_FILE_PREFIX, Long.toString(System.currentTimeMillis()));
            FileOutputStream fileOut = new FileOutputStream(file);
            wb.write(fileOut);
            fileOut.close();
            InputStream stream;
            stream = new FileInputStream(file);
            exportFile = new DefaultStreamedContent(stream, "text/csv", "export.xlsx");

        } catch (Exception e) {
            System.out.println(e);
        }
        return exportFile;
    }

    private void addSmile(Compound compound, Workbook wb, Sheet sheet, int col, int row) throws IOException {
        InputStream inputStream = OpenBabelUtils.getFile(compound.getSmiles());
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
