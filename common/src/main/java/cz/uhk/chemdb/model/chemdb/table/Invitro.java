package cz.uhk.chemdb.model.chemdb.table;

import cz.uhk.chemdb.utils.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DecimalFormat;
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

    private char valueoperator;

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

    @Override
    public String toString() {
        String sep = "; ";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getformatedValue());
        stringBuilder.append(sep);
        stringBuilder.append(errorType.name);
        if (!StringUtils.isEmpty(value_text)) {
            stringBuilder.append(sep);
            stringBuilder.append(value_text);
        }
        return stringBuilder.toString();
    }

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

    public String getformatedValue() {
        DecimalFormat decimalFormat = new DecimalFormat("##0.######");
        return decimalFormat.format(value);
    }

    public void setValue(double value) {
        this.value = value;
    }

    public char getValueoperator() {
        return valueoperator;
    }

    public void setValueoperator(char valueOperator) {
        this.valueoperator = valueOperator;
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

    public boolean contains(String searchString) {
        Integer i = Integer.MIN_VALUE;
        Float f = Float.NaN;
        Double d = Double.NaN;
        if (StringUtils.isNumeric(searchString)) {
            f = (float) StringUtils.getNumber(searchString, Float.class);
            i = (int) StringUtils.getNumber(searchString, Integer.class);
            d = (double) StringUtils.getNumber(searchString, Double.class);
        }
        if (conditions.contains(searchString)) return true;
        else if (citation.contains(searchString)) return true;
        else if (doi.contains(searchString)) return true;
        else if (note.contains(searchString)) return true;
        else if (errorType.getName().contains(searchString)) return true;
        else if (value_text.contains(searchString)) return true;
        else return value == d || (d < value && valueoperator == '<') || (d > value && valueoperator == '>');
    }
}
