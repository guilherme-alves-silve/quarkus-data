package br.com.guilhermealvessilve.resource;

import br.com.guilhermealvessilve.service.WeatherService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.format.DateTimeFormatter;

@Path("/weather")
public class WeatherResource {

    private final WeatherService service;

    @Inject
    public WeatherResource(final WeatherService service) {
        this.service = service;
    }

    @GET
    @Path("{city}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWeather(@PathParam("city") String city) {
        final var weather = service.getWeatherFrom(city);
        return Response.ok(weather)
                .build();
    }

    @GET
    @Path("{city}/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWeather(@PathParam("city") String city, @PathParam("date") String date) {
        final var weather = service.getWeatherFrom(city, date);
        return Response.ok(weather)
                .build();
    }

    @DELETE
    @Path("/cache/{city}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteWeather(@PathParam("city") String city) {
        service.invalidateWeatherCacheFromCity(city);
        return Response.noContent()
                .build();
    }

    @DELETE
    @Path("/cache/all")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAllWeather() {
        service.invalidateWeatherCacheFromAll();
        return Response.noContent()
                .build();
    }
}