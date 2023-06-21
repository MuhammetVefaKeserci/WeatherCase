package durumu.hava;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class WeatherR {
    private Main main;
    private List<Weather> weather;
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public static class Main {
        private double temp;



        public void setTemp(double temp) {
            this.temp = temp;
        }

        public double getTemp() {
            return temp;
        }
    }

    public static class Weather {
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}
