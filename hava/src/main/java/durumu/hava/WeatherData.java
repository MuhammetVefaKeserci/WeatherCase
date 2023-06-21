package durumu.hava;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;

@Entity
@Data
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@CrossOrigin
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    private Double temperature;
    private String weatherDescription;


    public WeatherData(String city, LocalDate date, double temp, String description) {

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