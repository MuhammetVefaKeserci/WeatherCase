package durumu.hava.repository;

import durumu.hava.dto.WeatherDataDTO;
import durumu.hava.entities.WeatherData;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface WeatherRepo extends JpaRepository<WeatherData, Long> {

    @Query("SELECT DISTINCT w.city FROM WeatherData w WHERE w.date = :date")
    List<String> findCitiesByDate(@Param("date") LocalDate date);



    /*@Modifying
    @Query("UPDATE WeatherData w SET w.is_deleted = true WHERE w.city = :city")
    @Transactional
    void deleteByCity(@Param("city") String city);*/

    @Transactional
    @Modifying
    @Query("UPDATE WeatherData w SET w.deleted = false WHERE w.city = :city")
    void recoverByCity(String city);


    @Query("SELECT DISTINCT w.city FROM WeatherData w WHERE w.deleted = true")
    List<String> getRecoverRepo();

    @Query("SELECT DISTINCT w.city FROM WeatherData w WHERE w.deleted = false")
    List<String> getResultLists();

    void deleteByCity(String weatherDataDTO);

    @Query("SELECT w FROM WeatherData w WHERE w.city = :city")
    List<WeatherData> getWeatherDataDTOByCity(String city);

    @Modifying
    @Transactional
    @Query("DELETE FROM WeatherData w WHERE w.city = :city")
    void deleteAllByCity(String city);
}
