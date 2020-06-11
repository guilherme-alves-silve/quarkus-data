package br.com.guilhermealvessilve.resource;

import br.com.guilhermealvessilve.data.Person;
import br.com.guilhermealvessilve.repository.PersonRepository;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("/person")
public class PersonResource {

    private static final Logger LOGGER = Logger.getLogger(PersonResource.class.getSimpleName());

    private final PersonRepository repository;

    @Inject
    public PersonResource(PersonRepository repository) {
        this.repository = repository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPersons() {
        return repository.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") Long id) {
        try {
            final var optPerson = repository.find(id);
            if (optPerson.isPresent()) {
                return Response.ok(repository.find(id))
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
    public Response postPerson(final Person person) {
        try {
            repository.save(person);
            return Response.created(new URI("/person/" + person.id))
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
    public Response putPerson(@PathParam("id") Long id, final Person person) {
        try {
            person.id = id;
            repository.update(person);
            return Response.ok(person)
                    .build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        return Response.serverError()
                .build();
    }
}