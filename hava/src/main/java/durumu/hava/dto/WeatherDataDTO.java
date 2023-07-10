package durumu.hava.dto;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class WeatherDataDTO {
    @Id
    private Long id;
    private String city;

}
