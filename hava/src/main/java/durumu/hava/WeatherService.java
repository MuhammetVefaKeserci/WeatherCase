package durumu.hava;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Data
@Getter
@Setter
public class WeatherService {

    private final WeatherRepo weatherRepo;

    public WeatherData saveWeatherData(WeatherData weatherData) {
        // Hava verisini veritabanına kaydetmek için WeatherRepo sınıfını kullanın
        weatherRepo.save(weatherData);
        return weatherData;
    }

    @Value("${openweathermap.api.key}")
    private String apiKey;

    public WeatherData getWeatherData(String city) {
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        WeatherData weatherData = restTemplate.getForObject(apiUrl, WeatherData.class, city, apiKey);

        return weatherData;
    }

}

