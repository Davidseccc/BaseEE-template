package cz.uhk.chemdb.model.chemdb.table;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "target")
public class Target implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String abbreviation;
    private String note;
    @ManyToOne
    private Invitro invitro;

    public Target() {
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

    public Invitro getInvitro() {
        return invitro;
    }

    public void setInvitro(Invitro invitro) {
        this.invitro = invitro;
    }
}

