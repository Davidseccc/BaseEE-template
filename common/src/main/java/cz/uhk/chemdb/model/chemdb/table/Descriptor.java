package cz.uhk.chemdb.model.chemdb.table;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "descriptor")
public class Descriptor implements Serializable {
    @Id
    private Long id;
    private String formula;
    private float mw;
    private int hbd;
    private int hba;
    private String rb;
    private float tpsa;
    private int atoms;
    private float clogp;
    @OneToOne
    private Compound compound;

    public Descriptor() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public float getMw() {
        return mw;
    }

    public void setMw(float mw) {
        this.mw = mw;
    }

    public int getHbd() {
        return hbd;
    }

    public void setHbd(int hbd) {
        this.hbd = hbd;
    }

    public int getHba() {
        return hba;
    }

    public void setHba(int hba) {
        this.hba = hba;
    }

    public String getRb() {
        return rb;
    }

    public void setRb(String rb) {
        this.rb = rb;
    }

    public float getTpsa() {
        return tpsa;
    }

    public void setTpsa(float tpsa) {
        this.tpsa = tpsa;
    }

    public int getAtoms() {
        return atoms;
    }

    public void setAtoms(int atoms) {
        this.atoms = atoms;
    }

    public float getClogp() {
        return clogp;
    }

    public void setClogp(float clogp) {
        this.clogp = clogp;
    }

    public Compound getCompound() {
        return compound;
    }

    public void setCompound(Compound compound) {
        this.compound = compound;
    }
}