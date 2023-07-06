package durumu.hava.schedule;

import durumu.hava.entities.WeatherData;
import durumu.hava.service.WeatherService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class WeatherDataScheduler {

    private final WeatherService weatherService;

    private final List<String> selectedCities = Arrays.asList("London", "Istanbul", "Ankara");

    public WeatherDataScheduler(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    /*fixedDelay = 120000*/ /*dakikalık kullanım için koydum*/
    @Scheduled(cron = "0 0 9-11 * * *")
    public void fetchAndSaveWeatherData() {
        for (String city : selectedCities) {
            WeatherData weatherData = weatherService.getWeatherDataForSchedule(city);

            if (weatherData != null) {
                weatherData.setCity(city);
                weatherData.setDate(LocalDate.now());

                weatherService.saveWeatherData(weatherData);
            }
        }
    }
}
