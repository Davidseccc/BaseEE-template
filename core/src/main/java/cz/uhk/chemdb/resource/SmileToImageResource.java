package cz.uhk.chemdb.resource;

import cz.uhk.chemdb.utils.OpenBabelUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@Path("/smile")
public class SmileToImageResource {

    @Inject
    OpenBabelUtils openBabelUtils;
    @GET
    @Path("/{smile}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getAllUsers(@PathParam("smile") String smile,
                                @Context SecurityContext securityContext) {
        InputStream targetStream = null;
        try {
            targetStream = OpenBabelUtils.getFile(smile);
        } catch (
                FileNotFoundException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
            //e.printStackTrace();
        }
        return Response
                .ok(targetStream, MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition", "attachment; " +
                        "filename = " + smile.replaceAll("[:\\\\/*\"?|<>']", "_") + ".png")
                .build();
    }

}
