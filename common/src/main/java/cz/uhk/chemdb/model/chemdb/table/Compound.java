package cz.uhk.chemdb.model.chemdb.table;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "compound")
public class Compound extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int k;
    @NotNull
    private String smiles;
    private String originalCodename;
    private String ion;
    private double mw;
    private String notes;
    @ManyToOne
    private Owner owner;
    @OneToOne(mappedBy = "compound", cascade = CascadeType.ALL, orphanRemoval = true)
    private MeltingPoint meltingPoint;
    @OneToOne(mappedBy = "compound", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Descriptor descriptor;
    @OneToMany(mappedBy = "compound", cascade = CascadeType.ALL)
    @OrderBy("name ASC")
    private Set<Synonymum> synonyms = new LinkedHashSet<>();

    @OneToOne(mappedBy = "compound", cascade = CascadeType.ALL, orphanRemoval = true)
    private DOI doi;

    @OneToMany(mappedBy = "compound", cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    Set<Invitro> invitro = new LinkedHashSet<>();

    @OneToMany(mappedBy = "compound", cascade = CascadeType.ALL)
    @OrderBy("ord ASC")
    private List<Attribute> attributes = new ArrayList<>();

    private LocalDateTime deletedAt;

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

    public double getMw() {
        return mw;
    }

    public void setMw(double mw) {
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

    public Set<Invitro> getInvitro() {
        return invitro;
    }

    public void setInvitro(Set<Invitro> invitro) {
        this.invitro = invitro;
    }

    public List<Attribute> getAttributes() {
        if (attributes == null) {
            return new ArrayList<>();
        }
        return attributes.stream().filter(attribute -> attribute.getDeletedAt() == null).collect(Collectors.toList());
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public MeltingPoint getMeltingPoint() {
        return meltingPoint;
    }

    public void setMeltingPoint(MeltingPoint meltingPoint) {
        this.meltingPoint = meltingPoint;
    }

    public String getOriginalCodename() {
        return originalCodename;
    }

    public void setOriginalCodename(String originalCodename) {
        this.originalCodename = originalCodename;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public DOI getDoi() {
        return doi;
    }

    public void setDoi(DOI doi) {
        this.doi = doi;
    }

    @Override
    public String toString() {
        return id + " - " + smiles;
    }

}
