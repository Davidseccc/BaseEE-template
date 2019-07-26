package cz.uhk.chemdb.model.chemdb.table;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "compound")
public class Compound extends BaseModel implements Serializable {
    @ManyToOne
    Invitro invitro;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int k;
    @NotNull
    private String smiles;
    private String ion;
    private float mw;
    private String notes;
    @OneToOne(mappedBy = "compound", cascade = CascadeType.ALL, orphanRemoval = true)
    private Descriptor descriptor;
    @OneToMany(mappedBy = "compound", cascade = CascadeType.ALL)
    @OrderBy("name ASC")
    private Set<Synonymum> synonyms = new LinkedHashSet<>();

    public Compound() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public String getSmiles() {
        return smiles;
    }

    public void setSmiles(String smiles) {
        this.smiles = smiles;
    }

    public String getIon() {
        return ion;
    }

    public void setIon(String ion) {
        this.ion = ion;
    }

    public float getMw() {
        return mw;
    }

    public void setMw(float mw) {
        this.mw = mw;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Descriptor getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(Descriptor descriptor) {
        this.descriptor = descriptor;
    }

    public Set<Synonymum> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(Set<Synonymum> synonyms) {
        this.synonyms = synonyms;
    }
}
