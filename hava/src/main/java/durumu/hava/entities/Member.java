package durumu.hava.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Entity
@Data
@Component
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    private String surname;

    /*  @ManyToMany
    @JoinTable(
            name = "member_selected_items",
            joinColumns = @JoinColumn(name = "selected_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<SelectedItems> selectedItems = new ArrayList<>();*/
    @ManyToMany
    @JoinTable(
            name = "member_selected_items",
            joinColumns = @JoinColumn(name = "members_user_id"),
            inverseJoinColumns = @JoinColumn(name = "selected_items_selected_id")
    )
    private List<SelectedItems> selectedItems = new ArrayList<>();

/*
    private Set<SelectedItems> selectedItems = new HashSet<>();
*/


}
