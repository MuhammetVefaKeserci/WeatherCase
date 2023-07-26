package durumu.hava.repository;

import durumu.hava.entities.SelectedItems;
import durumu.hava.entities.WeatherData;
import durumu.hava.requests.CitySelectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SelectedRepo extends JpaRepository<SelectedItems, Long> {

}
