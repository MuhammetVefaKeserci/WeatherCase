package durumu.hava.schedule;

import durumu.hava.entities.Member;
import durumu.hava.entities.SelectedItems;
import durumu.hava.entities.WeatherData;
import durumu.hava.repository.MemberRepo;
import durumu.hava.service.MemberService;
import durumu.hava.service.ScheduledService;
import durumu.hava.service.SelectedService;
import durumu.hava.service.WeatherService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class WeatherDataScheduler {

    private final WeatherService weatherService;

    private final ScheduledService scheduledService;

    private final MemberRepo memberRepo;


/*
    private final List<String> selectedCities = Arrays.asList("London", "Istanbul", "Ankara");
*/

    public WeatherDataScheduler(WeatherService weatherService, MemberRepo memberRepo, ScheduledService scheduledService) {
        this.weatherService = weatherService;
        this.memberRepo = memberRepo;
        this.scheduledService = scheduledService;
    }
/*
fixedDelay = 120000
dakikalık kullanım için koydum
*/
    /*cron = "0 0 9-11 * * *"*/

    @Scheduled(fixedDelay = 60000)
    public void fetchAndSaveWeatherData() {
/*
        List<String> selectedCities = memberRepo.getAllSelectedCities();
*/
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

