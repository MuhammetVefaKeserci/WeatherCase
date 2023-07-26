package durumu.hava.controller;

import durumu.hava.entities.Member;
import durumu.hava.entities.SelectedItems;
import durumu.hava.repository.MemberRepo;
import durumu.hava.requests.CitySelectionRequest;
import durumu.hava.service.MemberService;
import durumu.hava.service.SelectedService;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Data
@RequestMapping("/member")
@CrossOrigin(origins = "*")
public class MemberController {



    private MemberService memberService;
    private SelectedService selectedService;
    private MemberRepo memberRepo;

    public MemberController(MemberService memberService, SelectedService selectedService,MemberRepo memberRepo) {
        this.memberService = memberService;
        this.selectedService = selectedService;
        this.memberRepo = memberRepo;
    }



    @PostMapping("/select/items")
    @Transactional
    public ResponseEntity<String> selectCity(@RequestBody CitySelectionRequest request) {
        Optional<Member> optionalMember = memberService.getUserById(request.getUserId());
        Optional<SelectedItems> optionalCity = selectedService.getCityById(request.getCityId());

        if (optionalMember.isEmpty()) {
            return ResponseEntity.badRequest().body("Kullanıcı bulunamadı");
        }

        if (optionalCity.isEmpty()) {
            return ResponseEntity.badRequest().body("Geçersiz şehir ID'si");
        }

        Member member = optionalMember.get();
        SelectedItems city = optionalCity.get();
if (member.getSelectedItems().isEmpty()){
    member.getSelectedItems().add(city);
}
else {
    memberRepo.updateUser(request);

    city.getMembers().add(member);
    selectedService.updateCity(city);
}


        return ResponseEntity.ok("Seçilen şehir güncellendi: " + city.getCity());
    }

    @GetMapping("/selected")
    public ResponseEntity<Object> getSelectedCitiesByUserId(@RequestBody CitySelectionRequest selectionRequest) {
        List<Long> selectedCityIds = memberService.getSelectedCityIdsByUserId(selectionRequest.getUserId());
        return ResponseEntity.ok(selectedCityIds);
    }

    @GetMapping("/selected/city")
    public ResponseEntity<Object> getSelectedCities(@RequestBody Member member) {
        List<String> selectedCityIds = memberService.getSelectedCities(member.getUserId());
        return ResponseEntity.ok(selectedCityIds);
    }
}