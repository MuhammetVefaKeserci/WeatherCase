package durumu.hava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.time.LocalDate;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherRepo weatherRepo;
    private final RestTemplate restTemplate;

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(RestTemplate restTemplate, WeatherRepo weatherRepo, WeatherService weatherService) {
        this.weatherRepo = weatherRepo;
        this.weatherService = weatherService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{city}")
    public ResponseEntity<?> getWeather(@PathVariable("city") String city) {
        String apiKey = "b0d92ae513ff109d7758b825f0382832";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

        ResponseEntity<WeatherApiResponse> response = restTemplate.getForEntity(url, WeatherApiResponse.class);
        WeatherApiResponse weatherApiResponse = response.getBody();

        double temperature = weatherApiResponse.getMain().getTemp();
        String weatherDescription = weatherApiResponse.getWeather().get(0).getDescription();
        LocalDate currentDate = LocalDate.now();

        // Verileri veritabanına kaydetme
        WeatherData weatherData = new WeatherData();
        weatherData.setCity(city);
        weatherData.setDate(currentDate);
        weatherData.setTemperature(String.valueOf(temperature));
        weatherData.setWeatherDescription(weatherDescription);
        weatherRepo.save(weatherData);

        return ResponseEntity.status(response.getStatusCode()).body(weatherApiResponse);
    }

}
