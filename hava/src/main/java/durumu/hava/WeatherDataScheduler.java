package durumu.hava;

import durumu.hava.WeatherApiResponse;
import durumu.hava.WeatherData;
import durumu.hava.WeatherRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
public class WeatherDataScheduler {
    private final WeatherRepo weatherRepo;
    private final RestTemplate restTemplate;
    private final String apiKey;
    private final List<String> cities;

    public WeatherDataScheduler(WeatherRepo weatherRepo, RestTemplate restTemplate, String apiKey, List<String> cities) {
        this.weatherRepo = weatherRepo;
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.cities = cities;
    }

    @Scheduled(fixedRate = 60000) // Her 60 saniyede bir çalışacak şekilde ayarlandı
    public void fetchDailyWeatherData() {
        for (String city : cities) {
            String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
            try {
                WeatherApiResponse response = restTemplate.getForObject(url, WeatherApiResponse.class);
                if (response != null) {
                    WeatherData weatherData = new WeatherData(
                            city,
                            response.getDate(),
                            response.getMain().getTemp(),
                            response.getWeather().get(0).getDescription()
                    );
                    weatherRepo.save(weatherData);
                    System.out.println("Hava durumu verisi başarıyla alındı ve kaydedildi. Şehir: " + city);
                } else {
                    System.out.println("Hava durumu verisi alınamadı. Şehir: " + city);
                }
            } catch (Exception e) {
                System.err.println("Hava durumu verisi alınırken hata oluştu. Şehir: " + city);
                e.printStackTrace();
            }
        }
    }
}
