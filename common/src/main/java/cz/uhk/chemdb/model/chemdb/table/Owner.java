package cz.uhk.chemdb.model.chemdb.table;

import cz.uhk.chemdb.utils.StringUtils;

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

    public Owner() {
    }

    public Owner(String name) {
        this.name = name;
    }

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

    public boolean contains(String searchString) {
        return !StringUtils.isEmpty(name) && name.contains(searchString);
    }
}
