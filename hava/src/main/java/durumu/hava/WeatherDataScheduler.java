package durumu.hava;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class WeatherDataScheduler {
    private final WeatherService weatherService;

    public WeatherDataScheduler(WeatherService weatherService){
        this.weatherService = weatherService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void fetchDailyWeatherData(){
        List<String> cities = Arrays.asList("London", "New York", "Tokyo");

        for (String city : cities) {
            WeatherData weatherData = weatherService.getWeatherData(city);
            if (weatherData != null) {
                weatherService.saveWeatherData(weatherData);
            }
        }
    }
}
