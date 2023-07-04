package durumu.hava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class WeatherDataScheduler {

    private final WeatherService weatherService;

    private final List<String> selectedCities = Arrays.asList("London", "Istanbul", "Ankara");

    @Autowired
    public WeatherDataScheduler(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Scheduled(fixedDelay = 120000)
    public void fetchAndSaveWeatherData() {
        for (String city : selectedCities) {
            WeatherData weatherData = weatherService.getWeatherData(city);

            if (weatherData != null) {
                weatherData.setCity(city);
                weatherData.setDate(LocalDate.now());

                weatherService.saveWeatherData(weatherData);
            }
        }
    }
}
