package durumu.hava.schedule;

import durumu.hava.entities.WeatherData;
import durumu.hava.service.ScheduledService;
import durumu.hava.service.WeatherService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
public class WeatherDataScheduler {

    private final WeatherService weatherService;

    private final ScheduledService scheduledService;


    public WeatherDataScheduler(WeatherService weatherService, ScheduledService scheduledService) {
        this.weatherService = weatherService;
        this.scheduledService = scheduledService;
    }
/*
fixedDelay = 120000
dakikalık kullanım için koydum
*/

    @Scheduled(cron = "0 15 10 15 * ?")
    public void fetchAndSaveWeatherData() {
        List<String> selectedCities = scheduledService.findSelectedCities();

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

