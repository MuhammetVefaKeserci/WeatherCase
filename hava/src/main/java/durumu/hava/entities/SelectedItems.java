package durumu.hava.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Component
public class SelectedItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long selectedId;

    private String city;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "selectedItems")
    private List<Member> members = new ArrayList<>();


}
