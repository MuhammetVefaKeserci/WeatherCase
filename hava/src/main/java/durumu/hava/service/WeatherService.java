package durumu.hava.service;
import durumu.hava.response.WeatherApiResponse;
import durumu.hava.entities.WeatherData;
import durumu.hava.repository.WeatherRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.List;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private final WeatherRepo weatherRepo;
    private final WeatherData weatherData;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    public WeatherService(WeatherRepo weatherRepo, RestTemplate restTemplate, WeatherData weatherData) {
        this.weatherRepo = weatherRepo;
        this.restTemplate = restTemplate;
        this.weatherData = weatherData;
    }

    public void saveWeatherData(WeatherData weatherData) {

        weatherRepo.saved(weatherData);
    }


    public WeatherData getWeatherDataForSchedule(String city) {
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        WeatherApiResponse response = restTemplate.getForObject(apiUrl, WeatherApiResponse.class, city, apiKey);

        if (response != null) {
            WeatherApiResponse.Main main = response.getMain();
            WeatherApiResponse.Weather weather = response.getWeather().get(0);

            double temperature = main.getTemp();
            String weatherDescription = weather.getDescription();

            weatherData.setCity(city);
            weatherData.setTemperature(Double.parseDouble(String.valueOf(temperature)));
            weatherData.setWeatherDescription(weatherDescription);

            return weatherData;
        }

        return null;
    }

    public WeatherApiResponse getWeatherData(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
        ResponseEntity<WeatherApiResponse> response = restTemplate.getForEntity(url, WeatherApiResponse.class);
        return response.getBody();
    }

    public void saveWeatherData(WeatherApiResponse weatherApiResponse, String city) {

        double temperature = weatherApiResponse.getMain().getTemp();

        String weatherDescription = weatherApiResponse.getWeather().get(0).getDescription();

        LocalDate currentDate = LocalDate.now();

        weatherData.setCity(city);
        weatherData.setDate(currentDate);
        weatherData.setTemperature(temperature);
        weatherData.setWeatherDescription(weatherDescription);
        weatherRepo.saved(weatherData);
    }

    public List<String> findCities(LocalDate date) {

        List<String> cities = weatherRepo.findCitiesByDate(date);

        return cities;

    }
    /*@Transactional
    public WeatherApiResponse deleted(WeatherDataDTO weatherDataDTO) {

        weatherRepo.getWeatherDataDTOByCity(weatherDataDTO.getCity());

        return null;
    }*/

    @Transactional
    public void deleteByList(WeatherData weatherDataDTO){
        String city = weatherDataDTO.getCity();
        weatherRepo.deleteAllByCity(city);

    }



    public List<String> deleteWeatherData() {

        List<String> deleteItems = weatherRepo.getResultLists();

        return deleteItems;
    }
    
    


    public void recoverService(String city) {
        weatherRepo.recoverByCity(city);
    }
    
    public List<WeatherData> getWeatherDataByCity(WeatherData weatherDataDTO) {

        List<WeatherData> weatherDataDTOByCity = weatherRepo.getWeatherDataDTOByCity(weatherDataDTO.getCity());
        return weatherDataDTOByCity;
    }

    
    
    /*public void deleteService(String city) {
        weatherRepo.deleteByCity(city);
    }*/

    public List<String> getRecoverItems() {
        List<String> recoverItems = weatherRepo.getRecoverRepo();
        return  recoverItems;
    }

@Transactional
    public void deleteService(String city) {
        weatherRepo.deleteByCity(city);
    }

    public boolean getOneDeletedItems(WeatherData weatherData) {

        boolean resultLists2 = weatherRepo.getResultLists2(weatherData.getCity());

        return resultLists2;

    }

    public List<WeatherData> findByCity(String city) {
        List<WeatherData> byCity = weatherRepo.findByCity(city);
        return byCity;
    }
}



