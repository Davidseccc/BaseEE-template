package cz.uhk.chemdb.model.chemdb.table;

import cz.uhk.chemdb.utils.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "descriptor")
public class Descriptor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String formula;
    private float mw;
    private int hbd;
    private int hba;
    private String rb;
    private float tpsa;
    private int atoms;
    private float clogp;
    private String NMR;
    private String HRMS;
    private float purity;
    private Character purityOperator;
    private String solubility;

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

    public void setId(Long id) {
        this.id = id;
    }

    public String getNMR() {
        return NMR;
    }

    public void setNMR(String NMR) {
        this.NMR = NMR;
    }

    public String getHRMS() {
        return HRMS;
    }

    public void setHRMS(String HRMS) {
        this.HRMS = HRMS;
    }

    public float getPurity() {
        return purity;
    }

    public void setPurity(float purity) {
        this.purity = purity;
    }

    public char getPurityOperator() {
        return purityOperator;
    }

    public void setPurityOperator(char purityOperator) {
        this.purityOperator = purityOperator;
    }

    public String getSolubility() {
        return solubility;
    }

    public void setSolubility(String solubility) {
        this.solubility = solubility;
    }

    public boolean contains(String searchString) {
        Float f = Float.NaN;
        Integer i = Integer.MIN_VALUE;
        if (StringUtils.isNumeric(searchString)) {
            f = StringUtils.getNumber(searchString, Float.class).floatValue();
            i = StringUtils.getNumber(searchString, Integer.class).intValue();
        }
        if (!StringUtils.isEmpty(formula) && formula.contains(searchString)) return true;
        else if (mw == f) return true;
        else if (hba == i || hbd == i || i == atoms) return true;
        else if (!StringUtils.isEmpty(rb) && rb.contains(searchString)) return true;
        else if (f == tpsa || f == clogp) return true;
        else if (!StringUtils.isEmpty(NMR) && NMR.contains(searchString)) return true;
        else if (!StringUtils.isEmpty(HRMS) && HRMS.contains(searchString)) return true;
        else if (f == purity || (f < purity && purityOperator == '<') || (f > purity && purityOperator == '>'))
            return true;
        else return solubility.contains(searchString);
    }
}
