package cz.uhk.chemdb.model.chemdb.table;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "target")
public class Target implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String abbreviation;
    private String note;
    @OneToMany(mappedBy = "target", cascade = CascadeType.ALL)
    private Set<Invitro> invitros = new HashSet<>();

    public Target() {
    }

    public Target(String name) {
        this.name = name;
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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Set<Invitro> getInvitros() {
        return invitros;
    }

    public void setInvitros(Set<Invitro> invitros) {
        this.invitros = invitros;
    }
}

