package durumu.hava.requests;

import lombok.Data;

@Data
public class CitySelectionRequest {
    private Long userId;
    private Long cityId;
    private String city;
}
