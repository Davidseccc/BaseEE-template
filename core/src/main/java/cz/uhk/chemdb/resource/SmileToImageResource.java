package cz.uhk.chemdb.resource;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@Path("/smile")
public class SmileToImageResource {

    @GET
    @Path("/{smile}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getAllUsers(@PathParam("smile") String smile,
                                @Context SecurityContext securityContext) {

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
                System.out.println(line);
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
        try {
            targetStream = new FileInputStream(file);
        } catch (
                FileNotFoundException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
            //e.printStackTrace();
        }
        return Response
                .ok(targetStream, MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition", "attachment; filename = " + hash + ".png")
                .build();
    }

}
