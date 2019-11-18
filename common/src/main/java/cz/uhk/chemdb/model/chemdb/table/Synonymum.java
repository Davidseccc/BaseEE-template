package cz.uhk.chemdb.model.chemdb.table;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "synonymum")
public class Synonymum implements Serializable {

    String name;
    String note;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Compound compound;

    public boolean contains(String searchString) {
        if (name.contains(searchString)) return true;
        else return note.contains(searchString);
    }
}
