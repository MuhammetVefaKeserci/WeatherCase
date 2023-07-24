package durumu.hava.service;

import durumu.hava.entities.Member;
import durumu.hava.entities.SelectedItems;
import durumu.hava.repository.MemberRepo;
import durumu.hava.repository.SelectedRepo;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
public class MemberService {

    private final MemberRepo memberRepo;
private final SelectedRepo selectedRepo;


    public MemberService(MemberRepo memberRepo, SelectedRepo selectedRepo){
        this.memberRepo = memberRepo;


        this.selectedRepo = selectedRepo;
    }



    public Optional<Member> getUserById(Long memberId) {
        return memberRepo.findById(memberId);
    }

    public void updateUser(Member member1) {
        memberRepo.saveAndFlush(member1);
    }

    public List<Long> getSelectedCityIdsByUserId(Long userId) {
        Optional<Member> optionalMember = memberRepo.findById(userId);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            List<SelectedItems> selectedCities = member.getSelectedItems();
            return selectedCities.stream()
                    .map(SelectedItems::getSelectedId)
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Geçersiz kullanıcı ID'si");
        }
    }


    public List<String> getSelectedCities(Long userId) {
        Optional<Member> optionalMember = memberRepo.findById(userId);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            List<SelectedItems> selectedCities = member.getSelectedItems();
            return selectedCities.stream()
                    .map(SelectedItems::getCity)
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Geçersiz kullanıcı ID'si");
        }
    }

/*    public List<String> getSelectedCitiesByMember(Member member) {
        List<SelectedItems> selectedItems = member.getSelectedItems();
        return selectedItems.stream()
                .map(SelectedItems::getCity)
                .collect(Collectors.toList());
    }

    public Optional<Member> getUserById(Long memberId) {
        Optional<Member> member = memberRepo.findById(memberId);
        return member;
    }

    public List<Member> getAllMembers() {
        Member member = (Member) memberRepo.findAll();
        return (List<Member>) member;
    }*/
}
