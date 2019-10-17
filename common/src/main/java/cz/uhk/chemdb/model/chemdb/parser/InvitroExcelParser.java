package cz.uhk.chemdb.model.chemdb.parser;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InvitroExcelParser {

    public Invitro parse(String filePath) throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(filePath));
        List<Data> dataList = new ArrayList<>();
        Sheet sheet = workbook.getSheet("List1");
        DataFormatter dataFormatter = new DataFormatter();
        Invitro invitro = new Invitro();
        invitro.setTargetEnum(dataFormatter.formatCellValue(sheet.getRow(0).getCell(1)));
        invitro.setValueEnum(dataFormatter.formatCellValue(sheet.getRow(1).getCell(1)));
        invitro.setValueErrorEnum(dataFormatter.formatCellValue(sheet.getRow(1).getCell(3)));
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row.getFirstCellNum() == 0) {
                Data data = new Data();
                data.setId(dataFormatter.formatCellValue(row.getCell(0)));
                data.setValue(dataFormatter.formatCellValue(row.getCell(1)));
                data.setError(dataFormatter.formatCellValue(row.getCell(2)));
                data.setValueError(dataFormatter.formatCellValue(row.getCell(3)));
                dataList.add(data);
            }
        }
        invitro.setInvitoroData(dataList);
        workbook.close();
        return invitro;
    }

    public class Invitro {
        String targetEnum;
        String valueEnum;
        String valueErrorEnum;
        List<Data> invitoroData;

        public String getTargetEnum() {
            return targetEnum;
        }

        public void setTargetEnum(String targetEnum) {
            this.targetEnum = targetEnum;
        }

        public String getValueEnum() {
            return valueEnum;
        }

        public void setValueEnum(String valueEnum) {
            this.valueEnum = valueEnum;
        }

        public String getValueErrorEnum() {
            return valueErrorEnum;
        }

        public void setValueErrorEnum(String valueErrorEnum) {
            this.valueErrorEnum = valueErrorEnum;
        }

        public List<Data> getInvitoroData() {
            return invitoroData;
        }

        public void setInvitoroData(List<Data> invitoroData) {
            this.invitoroData = invitoroData;
        }

        @Override
        public String toString() {
            return "Invitro{" +
                    "targetEnum='" + targetEnum + '\'' +
                    ", valueEnum='" + valueEnum + '\'' +
                    ", valueErrorEnum='" + valueErrorEnum + '\'' +
                    ", invitroData'" + invitoroData.toString() + '\'' +
                    '}';
        }
    }

    public class Data {
        String id;
        String value;
        String error;
        String valueError;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getValueError() {
            return valueError;
        }

        public void setValueError(String valueError) {
            this.valueError = valueError;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", value='" + value + '\'' +
                    ", error='" + error + '\'' +
                    ", valueError='" + valueError + '\'' +
                    '}';
        }
    }
}
