package durumu.hava;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherRepo extends JpaRepository<WeatherData, Long> {
    @Override
    <S extends WeatherData> List<S> saveAll(Iterable<S> entities);

}
