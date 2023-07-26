package durumu.hava.repository;

import durumu.hava.entities.Member;
import durumu.hava.entities.SelectedItems;
import durumu.hava.requests.CitySelectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepo extends JpaRepository<Member, Long> {


    @Query("SELECT DISTINCT si.city FROM Member m JOIN m.selectedItems si")
    List<String> getAllSelectedCities();
    @Modifying
    @Query(value = "UPDATE member_selected_items SET selected_items_selected_id = :#{#request.cityId} WHERE members_user_id = :#{#request.userId}",nativeQuery = true)
    void updateUser(CitySelectionRequest request);


}
