package durumu.hava.service;

import durumu.hava.entities.SelectedItems;
import durumu.hava.repository.SelectedRepo;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class SelectedService {
    private final SelectedRepo selectedRepo;

    public SelectedService(SelectedRepo selectedRepo) {
        this.selectedRepo = selectedRepo;
    }

    public Optional<SelectedItems> getCityById(Long cityId) {
        return selectedRepo.findById(cityId);
    }

    public SelectedItems updateCity(SelectedItems city) {
        return selectedRepo.saveAndFlush(city);
    }



}
