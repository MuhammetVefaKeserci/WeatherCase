package durumu.hava.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@CrossOrigin
@Component
@SQLDelete(sql = "UPDATE weather_data SET deleted = true WHERE id=?")
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    private double temperature;

    private String weatherDescription;

/*    @Column(name = "is_deleted")
    private boolean is_deleted;

Bu eski yazdığım kod
*/
    @Column(name = "deleted")
    private Boolean deleted;

    public WeatherData() {

    }

}