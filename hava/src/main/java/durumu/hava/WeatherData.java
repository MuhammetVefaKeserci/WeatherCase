package durumu.hava;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Data
@Setter
@Getter
public class WeatherData {
    private String city;

    private LocalDate date;
    private double temperature;
    private String weatherDescription;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public WeatherData(LocalDate date) {

        this.date = date;
    }

    @JsonCreator
    public WeatherData(
        String city, double temperature, String weatherDescription
    ) {
        this.city = city;
        this.temperature = temperature;
        this.weatherDescription = weatherDescription;
    }

    public WeatherData() {

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = Double.parseDouble(temperature);
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

}