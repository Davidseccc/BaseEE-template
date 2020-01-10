package cz.uhk.chemdb.util.export;

import cz.uhk.chemdb.bean.CompoundDTO;
import cz.uhk.chemdb.utils.CSVExportFormats;
import cz.uhk.chemdb.utils.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.ejb.Singleton;
import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Singleton
public class CSVExport {

    public static StreamedContent startExport(CompoundDTO compound) {
        return getExportFile(compound);
    }

    public static StreamedContent getExportFile(CompoundDTO compound) {
        StreamedContent exportFile;
        try {
            File file = File.createTempFile(CSVExportFormats.TEMP_FILE_PREFIX, Long.toString(System.currentTimeMillis()));
            InputStream stream;

            String k = compound.getCompounds().get("k");
            Writer writer = createFile(file, compound, true);
            writeRecords(compound, writer);
            file = endFile(writer, file);
            stream = new FileInputStream(file);
            String fileName;
            fileName = String.format("k_%s.csv", k);
            exportFile = new DefaultStreamedContent(stream, "text/csv", fileName);
            return exportFile;

        } catch (IOException ex) {
            Logger.getGlobal().log(Level.SEVERE, ex.getMessage());
        }
        return null;
    }

    public static Writer createFile(File file, CompoundDTO compound, boolean printHeader) {
        try {
            FileOutputStream fileStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fileStream, CSVExportFormats.CHARSET);
            if (printHeader) {
                printHeader(writer, compound);
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

    private static void printHeader(Writer writer, CompoundDTO data) throws IOException {
        for (String key : data.getCompounds().keySet()) {
            append(writer, key);
            append(writer, CSVExportFormats.SEPARATOR);
        }
        for (String key : data.getTargets().keySet()) {
            Map<String, String> values = data.getTargets().get(key);
            for (String val : values.keySet()) {
                append(writer, String.format("%s_%s", key, val));
                append(writer, CSVExportFormats.SEPARATOR);
            }
        }
        for (String key : data.getAttributes().keySet()) {
            append(writer, key);
            append(writer, CSVExportFormats.SEPARATOR);
        }
        append(writer, CSVExportFormats.NEW_LINE);
    }

    private static void writeRecords(CompoundDTO data, Writer writer) throws IOException {
        for (String key : data.getCompounds().values()) {
            append(writer, key);
            append(writer, CSVExportFormats.SEPARATOR);
        }
        for (Map<String, String> key : data.getTargets().values()) {
            String mapToString = mapToString(key);
            Iterator<String> iterator = key.values().iterator();
            for (; ; ) {
                append(writer, iterator.next());
                if (!iterator.hasNext()) break;
                append(writer, CSVExportFormats.SEPARATOR);
            }
            append(writer, mapToString);
            append(writer, CSVExportFormats.SEPARATOR);
        }
        for (String key : data.getAttributes().values()) {
            append(writer, key);
            append(writer, CSVExportFormats.SEPARATOR);
        }
        append(writer, CSVExportFormats.NEW_LINE);
    }

    private static String mapToString(Map<String, String> map) {
        return map.values().stream().
                map(Object::toString).
                collect(Collectors.joining(String.format("%s", CSVExportFormats.SEPARATOR)));
    }

    private static void append(Writer writer, String str) throws IOException {
        if (StringUtils.isEmpty(str)) {
            writer.append("n/a");
        } else {
            writer.append(str.replaceAll(String.format("%s", CSVExportFormats.SEPARATOR), "_"));
        }
    }

    private static void append(Writer writer, char str) throws IOException {
        writer.append(str);
    }

}
