package durumu.hava;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeatherRepo extends JpaRepository<WeatherData, Long> {

    Optional<WeatherData> findTopByOrderByDateDesc();

    @Query("SELECT DISTINCT w.city FROM WeatherData w WHERE w.date = :date")
    List<String> findCitiesByDate(@Param("date") LocalDate date);
    ;
}
