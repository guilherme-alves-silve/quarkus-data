package br.com.guilhermealvessilve.resource;

import br.com.guilhermealvessilve.data.Client;
import br.com.guilhermealvessilve.repository.ClientRepository;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("/client")
public class ClientResource {

    private static final Logger LOGGER = Logger.getLogger(ClientResource.class.getSimpleName());

    private final ClientRepository repository;

    @Inject
    public ClientResource(ClientRepository repository) {
        this.repository = repository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> getClients() {
        return repository.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClient(@PathParam("id") Integer id) {
        try {
            final var optClient = repository.find(id);
            if (optClient.isPresent()) {
                return Response.ok(optClient.get())
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
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postClient(final Client client) {
        try {
            repository.save(client);
            return Response.created(new URI("/client/" + client.getId()))
                    .build();
        } catch (URISyntaxException ex) {
            LOGGER.error(ex.getMessage());
        }

        return Response.serverError()
                .build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putClient(@PathParam("id") Integer id, final Client client) {
        try {
            client.setId(id);
            repository.update(client);
            return Response.ok(client)
                    .build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        return Response.serverError()
                .build();
    }
}