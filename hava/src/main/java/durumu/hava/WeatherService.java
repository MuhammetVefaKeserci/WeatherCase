package durumu.hava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class WeatherService {

    private final WeatherRepo weatherRepo;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Autowired
    public WeatherService(WeatherRepo weatherRepo) {
        this.weatherRepo = weatherRepo;
    }

    public WeatherData saveWeatherData(WeatherData weatherData) {
        weatherRepo.save(weatherData);
        return weatherData;
    }


    public WeatherData getWeatherData(String city) {
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        WeatherApiResponse response = restTemplate.getForObject(apiUrl, WeatherApiResponse.class, city, apiKey);

        if (response != null) {
            WeatherApiResponse.Main main = response.getMain();
            WeatherApiResponse.Weather weather = response.getWeather().get(0);

            double temperature = main.getTemp();
            String weatherDescription = weather.getDescription();

            WeatherData weatherData = new WeatherData();
            weatherData.setCity(city);
            weatherData.setTemperature(String.valueOf(temperature));
            weatherData.setWeatherDescription(weatherDescription);

            return weatherData;
        }

        return null;
    }

}
