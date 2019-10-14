package cz.uhk.chemdb.model.chemdb.table;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "compoundowner")
public class Owner implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    List<Compound> compounds;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Compound> getCompounds() {
        return compounds;
    }

    public void setCompounds(List<Compound> compounds) {
        this.compounds = compounds;
    }
}
