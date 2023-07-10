package durumu.hava.controller;
import durumu.hava.dto.WeatherDataDTO;
import durumu.hava.entities.WeatherData;
import durumu.hava.response.WeatherApiResponse;
import durumu.hava.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Collections;
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

        if (!weatherApiResponse.getWeather().isEmpty()){

            weatherService.saveWeatherData(weatherApiResponse, city);

            return ResponseEntity.ok(weatherApiResponse);
        }
        else {
            return ResponseEntity.badRequest().body("şehir bulunamadı");
        }


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
         @DeleteMapping("/delete")
        public ResponseEntity<String> deleteWeather(@RequestBody WeatherData weatherDataDTO) {
             List<WeatherData> city = weatherService.getWeatherDataByCity( weatherDataDTO);
             if (!city.isEmpty()) {
                 weatherService.deleteByList(weatherDataDTO);
                 return ResponseEntity.ok("success");
             } else if (city.isEmpty()) {
                 return ResponseEntity.badRequest().body("error");
             }
             else {
                 return null;
             }


         }



/*    @PatchMapping("/{deleteItem}")
    public ResponseEntity<?> deleteWeather(@PathVariable("deleteItem") String city) {
            weatherService.deleteService(city);
            return ResponseEntity.ok().build();
    }*/

    @PatchMapping("recover")
    public ResponseEntity<?> recoverWeather(@RequestBody WeatherDataDTO weatherDataDTO){

        weatherService.recoverService(weatherDataDTO);

        return ResponseEntity.ok("succesfull");

    }

    @GetMapping("getDeletedItems")
    public ResponseEntity<List<String>> getDeletedItems() {

        List<String> deletedItems = weatherService.deleteWeatherData();

        if (deletedItems.isEmpty()){

            return ResponseEntity.badRequest().body(Collections.singletonList("Sistemde Bir Hata Meydana Geldi"));

        }
        else {

        return ResponseEntity.ok(deletedItems);

        }
    }

    @GetMapping("getRecoverItems")
    public ResponseEntity<?> getRecoverItems(){

        List<String> recoverItems = weatherService.getRecoverItems();

        return ResponseEntity.ok(recoverItems);
    }
}



 /*   @GetMapping("getDeletedItems")
    public ResponseEntity<List<Boolean>> getDeletedItems(){
        List<Boolean> deletedItems = weatherRepo.getResultLists();
        return ResponseEntity.ok(deletedItems);
        weatherRepo.getResultLists(is_deleted);
        return ResponseEntity.ok().build();
    }*/



