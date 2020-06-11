package br.com.guilhermealvessilve.resource;

import br.com.guilhermealvessilve.repository.NumberRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/number")
public class NumberResource {

    @Inject
    NumberRepository repository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Integer> getNumbers() {
        return repository.getAll();
    }
}