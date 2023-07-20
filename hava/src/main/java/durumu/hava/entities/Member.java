package durumu.hava.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@Component
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    private String surname;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<SelectedItems> selectedItems = new ArrayList<>();


}
