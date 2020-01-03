package cz.uhk.chemdb.util.export;

import cz.uhk.chemdb.model.chemdb.table.Compound;
import cz.uhk.chemdb.utils.CSVExportFormats;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.ejb.Singleton;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


@Singleton
public class CSVExport {

    public static StreamedContent startExport(Compound compound) {
        return getExportFile(compound);
    }

    public static StreamedContent getExportFile(Compound compound) {
        StreamedContent exportFile;
        try {
            File file = File.createTempFile(CSVExportFormats.TEMP_FILE_PREFIX, Long.toString(System.currentTimeMillis()));
            InputStream stream;

            Writer writer = createFile(file, true);
            writeRecords(compound, writer);
            file = endFile(writer, file);
            stream = new FileInputStream(file);
            String fileName;
            fileName = String.format("k_%s.csv", compound.getK());
            exportFile = new DefaultStreamedContent(stream, "text/csv", fileName);
            return exportFile;

        } catch (IOException ex) {
            Logger.getGlobal().log(Level.SEVERE, ex.getMessage());
        }
        return null;
    }

    public static Writer createFile(File file, boolean printHeader) {
        try {
            FileOutputStream fileStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fileStream, CSVExportFormats.CHARSET);
            if (printHeader) {
                printHeader(writer);
            }
            return writer;
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.SEVERE, ex.getMessage());
        }
        return null;
    }

    public static File endFile(Writer writer, File file) throws IOException {
        writer.flush();
        writer.close();
        return file;
    }

    private static void printHeader(Writer writer) throws IOException {
        writer.append("Uživatel");
        writer.append(CSVExportFormats.SEPARATOR);
        writer.append("Datum");
        writer.append(CSVExportFormats.SEPARATOR);
        writer.append("Hodnota");
        writer.append(CSVExportFormats.NEW_LINE);
    }

    private static void writeRecords(Compound data, Writer writer) throws IOException {
        writer.append(String.format("%s", data.getK()));
        writer.append(CSVExportFormats.NEW_LINE);
    }

}
