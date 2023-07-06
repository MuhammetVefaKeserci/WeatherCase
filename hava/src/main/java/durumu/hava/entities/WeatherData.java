package durumu.hava.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@CrossOrigin
@Component
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    private double temperature;

    private String weatherDescription;

    @Column(name = "is_deleted")
    private boolean is_deleted;
    @JsonCreator
    public WeatherData(
        String city, double temperature, String weatherDescription, boolean is_deleted
    ) {
        this.city = city;
        this.temperature = temperature;
        this.weatherDescription = weatherDescription;
        this.is_deleted = is_deleted;
    }

    public WeatherData() {

    }

}