package durumu.hava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherRepo weatherRepo;
    private final RestTemplate restTemplate;

    private WeatherService weatherService;

    @Autowired
    public WeatherController(RestTemplate restTemplate, WeatherRepo weatherRepo, WeatherService weatherService) {
        this.weatherRepo = weatherRepo;
        this.weatherService = weatherService;
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void DateController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/cities/{date}")
    public ResponseEntity<List<String>> getCitiesByDate(@PathVariable("date") LocalDate date) {
        List<String> cities = weatherRepo.findCitiesByDate(date);

        if (!cities.isEmpty()) {
            return ResponseEntity.ok(cities);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
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

        WeatherData weatherData = new WeatherData();
        weatherData.setCity(city);
        weatherData.setDate(currentDate);
        weatherData.setTemperature(String.valueOf(temperature));
        weatherData.setWeatherDescription(weatherDescription);
        weatherRepo.save(weatherData);

        return ResponseEntity.status(response.getStatusCode()).body(weatherApiResponse);
    }

}
