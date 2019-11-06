package cz.uhk.chemdb.model.chemdb.table;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "invitro")
public class Invitro implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Compound compound;

    private double value;

    private String value_text;
    @OneToMany(mappedBy = "invitro", cascade = CascadeType.ALL)
    @OrderBy("name ASC")
    private Set<Quantity> quantities;

    @ManyToOne
    @OrderBy("name ASC")
    private Target target;

    @ManyToOne
    @OrderBy("name ASC")
    private Organism organism;

    private String conditions;

    private String citation;

    private String doi;

    private String note;

    @Enumerated(EnumType.STRING)
    private ErrorType errorType;

    public Invitro() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Compound getCompound() {
        return compound;
    }

    public void setCompound(Compound compound) {
        this.compound = compound;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getValue_text() {
        return value_text;
    }

    public void setValue_text(String value_text) {
        this.value_text = value_text;
    }

    public Set<Quantity> getQuantities() {
        return quantities;
    }

    public void setQuantities(Set<Quantity> quantities) {
        this.quantities = quantities;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getCitation() {
        return citation;
    }

    public void setCitation(String citation) {
        this.citation = citation;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Organism getOrganism() {
        return organism;
    }

    public void setOrganism(Organism organism) {
        this.organism = organism;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorTypeId) {
        this.errorType = errorTypeId;
    }
}
