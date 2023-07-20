package durumu.hava.repository;

import durumu.hava.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepo extends JpaRepository<Member, Long> {


    @Query("SELECT DISTINCT si.city FROM Member m JOIN m.selectedItems si")
    List<String> getAllSelectedCities();}
