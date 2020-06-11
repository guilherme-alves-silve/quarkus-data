package br.com.guilhermealvessilve.resource;

import br.com.guilhermealvessilve.data.Author;
import br.com.guilhermealvessilve.service.AuthorService;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Path("/author")
public class AuthorResource {

    private static final int MAX_SIZE_SEARCH_SIZE = 50;
    private static final Logger LOGGER = Logger.getLogger(AuthorResource.class.getSimpleName());

    private final AuthorService service;

    @Inject
    public AuthorResource(AuthorService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Author> getAuthors() {
        return service.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthor(@PathParam("id") Long id) {
        try {
            final var optAuthor = service.get(id);
            if (optAuthor.isPresent()) {
                return Response.ok(optAuthor.get())
                        .build();
            }

            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        return Response.serverError()
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postAuthor(final Author author) {
        try {
            service.save(author);
            return Response.created(new URI("/author/" + author.id))
                    .build();
        } catch (URISyntaxException ex) {
            LOGGER.error(ex.getMessage());
        }

        return Response.serverError()
                .build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putAuthor(@PathParam("id") Long id, final Author author) {
        try {
            Optional<Author> optAuthor = service.update(id, author);
            if (optAuthor.isPresent()) {
                return Response.ok(optAuthor.get())
                        .build();
            }

            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        return Response.serverError()
                .build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAuthor(@PathParam("id") Long id) {
        try {
            final var optAuthor = service.delete(id);
            if (optAuthor.isPresent()) {
                return Response.ok(optAuthor.get())
                        .build();
            }

            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        return Response.serverError()
                .build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Author> searchForAuthor(
            @QueryParam("pattern") String pattern,
            @QueryParam("size") Optional<Integer> size
    ) {
        return service.searchForAuthor(pattern, size.orElse(MAX_SIZE_SEARCH_SIZE));
    }
}