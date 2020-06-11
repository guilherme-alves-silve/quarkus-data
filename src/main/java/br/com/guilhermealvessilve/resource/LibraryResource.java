package br.com.guilhermealvessilve.resource;

import br.com.guilhermealvessilve.data.Library;
import br.com.guilhermealvessilve.repository.LibraryRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

@Path("/library")
public class LibraryResource {

    private final LibraryRepository repository;

    @Inject
    public LibraryResource(LibraryRepository repository) {
        this.repository = repository;
    }

    @GET
    @Path("/cache/names")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<String> getCacheNames() {
        return repository.getCacheNames();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Library getLibrary(@PathParam("id") String id) {
        return repository.get(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postLibrary(final Library library) throws URISyntaxException {
        repository.save(library);
        return Response.created(new URI("/library/" + library.getId()))
                .build();
    }
}
