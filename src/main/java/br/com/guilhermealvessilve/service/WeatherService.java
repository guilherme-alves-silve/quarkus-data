package br.com.guilhermealvessilve.service;

import br.com.guilhermealvessilve.data.Weather;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@ApplicationScoped
public class WeatherService {

    private static final int CALL_TO_WEB_SERVICE_TIME_SIMULATION = 2000;
    private static final String CACHE_NAME_WEATHER = "city-weather";
    private static final Logger LOGGER = Logger.getLogger(WeatherService.class.getSimpleName());

    @CacheResult(cacheName = CACHE_NAME_WEATHER)
    public Weather getWeatherFrom(final String city) {

        final var status = getWeatherStatus();
        return new Weather(city, status, LocalDate.now().format(DateTimeFormatter.ISO_DATE));
    }

    @CacheResult(cacheName = CACHE_NAME_WEATHER)
    public Weather getWeatherFrom(@CacheKey final String city, String date) {

        final var status = getWeatherStatus();
        return new Weather(city, status, date);
    }


    @CacheInvalidate(cacheName = CACHE_NAME_WEATHER)
    public void invalidateWeatherCacheFromCity(String city) {
        LOGGER.info("Invalidating weather cache from city " + city);
    }

    @CacheInvalidateAll(cacheName = CACHE_NAME_WEATHER)
    public void invalidateWeatherCacheFromAll() {
        LOGGER.info("Invalidating weather cache from all cities");
    }

    private String getWeatherStatus() {

        try {
            Thread.sleep(CALL_TO_WEB_SERVICE_TIME_SIMULATION);
        } catch (InterruptedException ex) {
            LOGGER.error(ex);
        }

        return new Random().nextBoolean()? "Sunny" : "Rainy";
    }
}
