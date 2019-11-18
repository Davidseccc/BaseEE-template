package cz.uhk.chemdb.model.chemdb.table;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "attribute")
public class Attribute extends BaseModel implements Serializable {
    @ManyToOne
    Compound compound;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private AttributeType attributeType;
    private Integer ord;
    private String key;
    private String value;
    private LocalDateTime deletedAt;

    public Attribute() {
    }

    public Attribute(AttributeType attributeType, String key, String value, Integer order) {
        this.attributeType = attributeType;
        this.key = key;
        this.value = value;
        this.ord = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public Compound getCompound() {
        return compound;
    }

    public void setCompound(Compound compound) {
        this.compound = compound;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s)", key, value, ord);
    }

    public boolean contains(String searchString) {
        if (attributeType.getName().contains(searchString)) return true;
        if (attributeType.name().contains(searchString)) return true;
        if (key.contains(searchString)) return true;
        return value.contains(searchString);
    }
}
