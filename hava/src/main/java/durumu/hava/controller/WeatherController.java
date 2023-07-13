package durumu.hava.controller;
import durumu.hava.entities.WeatherData;
import durumu.hava.response.WeatherApiResponse;
import durumu.hava.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
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


    @GetMapping("/city")
    public ResponseEntity<?> getWeather(@RequestBody WeatherData city) {

        WeatherApiResponse weatherApiResponse = weatherService.getWeatherData(city.getCity());

            weatherService.saveWeatherData(weatherApiResponse, city.getCity());

            return ResponseEntity.ok(weatherApiResponse);

    }

    @GetMapping("/cities/date")
    public ResponseEntity<List<String>> getCitiesByDate(@RequestBody WeatherData date) {

        List<String> cities = weatherService.findCities(date.getDate());

        if (!cities.isEmpty()) {

            return ResponseEntity.ok(cities);

        }
        else {
              return ResponseEntity.badRequest().body(Collections.singletonList("Bu tarihte veri bulunamadı"));
        }
    }
    @DeleteMapping("/deleteAllSameData")
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


    @DeleteMapping("/deleteItem")
    public ResponseEntity<String> delete(@RequestBody WeatherData weatherDataDTO) {
        try {
            List<WeatherData> byCity = weatherService.findByCity(weatherDataDTO.getCity());
            /*Boolean b=byCity.isEmpty()*/
            if(byCity.isEmpty()){
                return ResponseEntity.badRequest().body("bu öğe database de mevcut değil");
            }
            else {
                boolean isDeleted = weatherService.getOneDeletedItems(weatherDataDTO);
                if (isDeleted ==  true) {
                    return ResponseEntity.badRequest().body("Bu öğe silinmiş");
                }
                else{
                    weatherService.deleteService(weatherDataDTO.getCity());
                    return ResponseEntity.ok("Başarılı");
                }
            }

        } catch (HttpClientErrorException.NotFound ex) {
            String responseBody = ex.getResponseBodyAsString();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
    }

    @PatchMapping("recover")
    public ResponseEntity<?> recoverWeather(@RequestBody WeatherData weatherData){
    try {
    List<WeatherData> byCity = weatherService.findByCity(weatherData.getCity());
    if (byCity.isEmpty()) {
        return ResponseEntity.badRequest().body("yanlış bilgi girişi");
    } else {
        boolean isDeleted = weatherService.getOneDeletedItems(weatherData);
        if (isDeleted == false) {
            return ResponseEntity.badRequest().body("Bu öğe database de mevcut");
        } else {
            weatherService.recoverService(weatherData.getCity());
            return ResponseEntity.ok("Başarılı");
        }
    }
    }
    catch (HttpClientErrorException.NotFound e){
        String responseBody = e.getResponseBodyAsString();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }
    }

    @GetMapping("getDeletedItems")
    public ResponseEntity<List<String>> getDeletedItems() {

        List<String> deletedItems = weatherService.deleteWeatherData();

        if (deletedItems.isEmpty()){

            return ResponseEntity.badRequest().body(Collections.singletonList("Silinen öğe bulunamadı"));

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



