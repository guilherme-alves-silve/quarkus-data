package br.com.guilhermealvessilve.data;

import lombok.Getter;

import javax.json.bind.annotation.JsonbCreator;

@Getter
public class Weather {

    private final String city;

    private final String status;

    private final String date;

    @JsonbCreator
    public Weather(String city, String status, String date) {
        this.city = city;
        this.status = status;
        this.date = date;
    }
}
