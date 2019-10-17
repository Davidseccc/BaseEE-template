package cz.uhk.chemdb.model.chemdb.parser;

import org.apache.poi.ss.usermodel.*;

import javax.ejb.Singleton;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public abstract class ExcelParser {

    public List<String> parse(String filePath) throws IOException {
        List<String> cells = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(new File(filePath));

        Sheet sheet = workbook.getSheet("List1");
        DataFormatter dataFormatter = new DataFormatter();

        for (Row row : sheet) {
            for (Cell cell : row) {
                cells.add(dataFormatter.formatCellValue(cell));
            }
        }

        return cells;
    }



}
