package durumu.hava;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class WeatherApiResponse {
    private Main main;
    private List<Weather> weather;
    private LocalDate date;

    @Data
    public static class Main {
        private double temp;
    }

    @Data
    public static class Weather {
        private String description;
    }

}
