package durumu.hava.repository;

import durumu.hava.entities.SelectedItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectedRepo extends JpaRepository<SelectedItems, Long> {
}
