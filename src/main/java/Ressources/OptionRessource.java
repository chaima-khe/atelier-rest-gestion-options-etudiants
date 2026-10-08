package Ressources;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/options")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OptionRessource {

    private OptionBusiness optionBusiness = new OptionBusiness();

    @POST
    public Response addOption(Option option) {
        if (option != null && optionBusiness.addOption(option)) {
            return Response.ok(option).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // A.2  GET /options                      -> toutes les options
    // A.3  GET /options?domaine=Mathématiques -> options d'un domaine
    @GET
    public Response getOptions(@QueryParam("domaine") String domaine) {
        List<Option> liste;
        if (domaine == null || domaine.trim().isEmpty()) {
            liste = optionBusiness.getListeOptions();
        } else {
            liste = optionBusiness.getOptionsByDomaine(domaine);
        }
        return Response.ok(liste).build();
    }

    // A.6  GET /options/1  ->  200 Ok | 404 Not Found
    @GET
    @Path("/{code}")
    public Response getByCode(@PathParam("code") int code) {
        Option option = optionBusiness.getOptionByCode(code);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(option).build();
    }

    // A.4  DELETE /options/2  ->  204 No Content | 404 Not Found
    @DELETE
    @Path("/{code}")
    public Response deleteOption(@PathParam("code") int code) {
        if (optionBusiness.deleteOption(code)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // A.5  PUT /options/1  ->  200 Ok | 404 Not Found
    @PUT
    @Path("/{code}")
    public Response updateOption(@PathParam("code") int code, Option option) {
        if (option != null && optionBusiness.updateOption(code, option)) {
            return Response.ok(option).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
