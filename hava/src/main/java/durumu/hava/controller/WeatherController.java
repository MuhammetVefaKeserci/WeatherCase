package durumu.hava.controller;
import durumu.hava.entities.WeatherData;
import durumu.hava.response.WeatherApiResponse;
import durumu.hava.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private static final Logger LOGGER = Logger.getLogger(WeatherController.class.getName());



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

        List<WeatherData> city = weatherService.getWeatherDataByCity(weatherDataDTO);

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
    public CompletableFuture<String> delete(@RequestBody WeatherData weatherDataDTO) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<WeatherData> byCity = weatherService.findByCity(weatherDataDTO.getCity());

                if (byCity.isEmpty()) {
                    return "Bu öğe veritabanında mevcut değil";
                } else {
                    CompletableFuture<Boolean> isDeletedFuture = weatherService.getOneDeletedItems(weatherDataDTO);

                    boolean isDeleted = isDeletedFuture.get();

                    if (isDeleted) {
                        return "Bu öğe silinmiş";
                    } else {
                        weatherService.deleteService(weatherDataDTO.getCity());
                        return "Başarılı";
                    }
                }
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Beklenmedik hata");
                return "Bir hata oluştu";
            }
        });
    }


    /*@PatchMapping("recover")
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
    }*/

    @PatchMapping("/recover")
    public CompletableFuture<ResponseEntity<?>> recoverWeather(@RequestBody WeatherData weatherData) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<WeatherData> byCity = weatherService.findByCity(weatherData.getCity());
                if (byCity.isEmpty()) {
                    return ResponseEntity.badRequest().body("Yanlış bilgi girişi");
                } else {
                    CompletableFuture<Boolean> isDeletedFuture = weatherService.getOneDeletedItems(weatherData);

                    boolean isDeleted = isDeletedFuture.get();

                    if (!isDeleted) {
                        return ResponseEntity.badRequest().body("Bu öğe veritabanında mevcut");
                    } else {
                        weatherService.recoverService(weatherData.getCity());
                        return ResponseEntity.ok("Başarılı");
                    }
                }
            } catch (Exception e) {
                LOGGER.log(Level.INFO, "Beklenmedik hata oluştu");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Bir hata oluştu");
            }
        });
    }

    @Async
    @GetMapping("getDeletedItems")
    public CompletableFuture<Object> getDeletedItems() {
return CompletableFuture.supplyAsync(() -> {
        List<String> deletedItems = weatherService.deleteWeatherData();

        if (deletedItems.isEmpty()){
            LOGGER.log(Level.INFO, "Silinen öğe bulunamadı");
            return "silinen öğe bulunamadı";

        }
        else {

        /*return ResponseEntity.ok(deletedItems);*/
            LOGGER.log(Level.INFO, "Silinen Öğeler:" + deletedItems);
            return CompletableFuture.completedFuture(deletedItems);

        }
    });
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



