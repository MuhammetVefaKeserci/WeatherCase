package durumu.hava.controller;
import durumu.hava.response.WeatherApiResponse;
import durumu.hava.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    @GetMapping("/{city}")
    public ResponseEntity<?> getWeather(@PathVariable("city") String city) {
        WeatherApiResponse weatherApiResponse = weatherService.getWeatherData(city);
        weatherService.saveWeatherData(weatherApiResponse, city);
        return ResponseEntity.ok(weatherApiResponse);
    }

    @GetMapping("/cities/{date}")
    public ResponseEntity<List<String>> getCitiesByDate(@PathVariable("date") LocalDate date) {
        List<String> cities = weatherService.findCities(date);

        if (!cities.isEmpty()) {
            return ResponseEntity.ok(cities);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{deleteItem}")
    public ResponseEntity<?> deleteWeather(@PathVariable("deleteItem") String city) {
            weatherService.deleteService(city);
            return ResponseEntity.ok().build();


    }

    @PatchMapping("recover/{recoverItem}")
    public ResponseEntity<?> recoverWeather(@PathVariable("recoverItem") String city){
        weatherService.recoverService(city);
        return ResponseEntity.ok().build();
    }

    @GetMapping("getDeletedItems")
    public ResponseEntity<List<String>> getDeletedItems() {
        List<String> deletedItems = weatherService.deleteWeatherData();
        return ResponseEntity.ok(deletedItems);
    }

    @GetMapping("getRecoverItems")
    public ResponseEntity<?> getRecoverItems(){
        List<String> recoverItems = weatherService.getRecoverItems();
        return ResponseEntity.ok(recoverItems);
    }




/*    @GetMapping("getDeletedItems")
    public ResponseEntity<List<Boolean>> getDeletedItems(){
        List<Boolean> deletedItems = weatherRepo.getResultLists();
        return ResponseEntity.ok(deletedItems);
       *//* weatherRepo.getResultLists(is_deleted);
        return ResponseEntity.ok().build();*//*
    }*/


}
