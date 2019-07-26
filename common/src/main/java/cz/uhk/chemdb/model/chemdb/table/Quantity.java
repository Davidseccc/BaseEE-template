package cz.uhk.chemdb.model.chemdb.table;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "quantity")
public class Quantity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String abbreviation;
    private String unit;
    private String note;
    @ManyToOne
    private Invitro invitro;

    public Quantity() {
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
