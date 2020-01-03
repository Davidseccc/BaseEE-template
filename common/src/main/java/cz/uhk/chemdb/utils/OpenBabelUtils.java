package cz.uhk.chemdb.utils;

import javax.ejb.Singleton;
import javax.inject.Named;
import java.io.*;

@Named
@Singleton
public class OpenBabelUtils implements Serializable {


    /**
     * Openbabel library required
     * ref: https://openbabel.org/docs/dev/Installation/install.html
     **/
    public static InputStream getFile(String smile) throws FileNotFoundException {
        int hash = smile.hashCode();
        String cmd = String.format("obabel -:\"%s\" -O ", smile) + String.format("/tmp/%s.png", hash) + " --genalias -xA";
        ProcessBuilder builder = new ProcessBuilder(
                "bash", "-c", cmd);
        builder.redirectErrorStream(true);
        Process p = null;

        try {
            p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
            }
            p.destroy();
        } catch (Exception e) {
            e.getStackTrace();
            e.printStackTrace();
        } finally {
            if (p != null) p.destroy();
        }

        File file = new File(String.format("/tmp/%s.png", hash));
        InputStream targetStream = null;
        String str = null;
        return new FileInputStream(file);
    }
}
