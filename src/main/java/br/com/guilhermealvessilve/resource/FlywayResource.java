package br.com.guilhermealvessilve.resource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Path("/flyway")
public class FlywayResource {

    private final Flyway flyway;

    @Inject
    public FlywayResource(final Flyway flyway) {
        this.flyway = flyway;
    }

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MigrationInfo> info() {
        return Arrays.stream(flyway.info().all())
                .collect(Collectors.toList());
    }
}
