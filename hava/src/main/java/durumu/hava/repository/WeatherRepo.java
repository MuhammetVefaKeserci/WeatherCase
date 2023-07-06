package durumu.hava.repository;

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
    @Modifying
    @Query("UPDATE WeatherData w SET w.is_deleted = true WHERE w.city = :city")
    @Transactional
    void deleteByCity(@Param("city") String city);

    @Transactional
    @Modifying
    @Query("UPDATE WeatherData w SET w.is_deleted = false WHERE w.city = :city")
    void recoverByCity(String city);

/*
    SELECT w FROM WeatherData w WHERE w.is_deleted = true
*/
@Query("SELECT DISTINCT w.city FROM WeatherData w WHERE w.is_deleted = false")
List<String> getResultLists();



}
