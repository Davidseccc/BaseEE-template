package cz.uhk.chemdb.model.chemdb.table;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "organism")
public class Organism implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "organism", cascade = CascadeType.MERGE)
    private Set<Invitro> invitros;


    public Organism() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Invitro> getInvitros() {
        return invitros;
    }

    public void setInvitros(Set<Invitro> invitros) {
        this.invitros = invitros;
    }
}

