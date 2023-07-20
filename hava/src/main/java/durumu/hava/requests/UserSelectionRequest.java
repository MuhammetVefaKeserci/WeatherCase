package durumu.hava.requests;

import durumu.hava.entities.Member;
import durumu.hava.entities.SelectedItems;
import lombok.Data;

import java.util.List;
@Data
public class UserSelectionRequest {
    private List<SelectedItems> selectedItems;

    private List<Member> member;

    public UserSelectionRequest() {
    }



}
