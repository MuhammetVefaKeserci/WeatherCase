package durumu.hava.service;

import durumu.hava.repository.MemberRepo;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class ScheduledService {

    private final MemberRepo memberRepo;

    public ScheduledService(MemberRepo memberRepo){
        this.memberRepo = memberRepo;
    }
    public List<String> findSelectedCities() {
        List<String> repoinfo = memberRepo.getAllSelectedCities();
        return repoinfo;
    }
}
