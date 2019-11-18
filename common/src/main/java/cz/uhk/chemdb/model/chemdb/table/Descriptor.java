package cz.uhk.chemdb.model.chemdb.table;

import cz.uhk.chemdb.utils.ObprpService;
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

    Double mol_weight;
    Double exact_mass;
    String canonical_SMILES;
    String InChI;
    Integer num_atoms;
    Integer num_bonds;
    Integer num_residues;
    Integer num_rotors;
    Integer sequence;
    Integer num_rings;
    Double logP;
    Double PSA;
    Double MR;


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

    public void setPurityOperator(Character purityOperator) {
        this.purityOperator = purityOperator;
    }

    public Double getMol_weight() {
        return mol_weight;
    }

    public void setMol_weight(Double mol_weight) {
        this.mol_weight = mol_weight;
    }

    public Double getExact_mass() {
        return exact_mass;
    }

    public void setExact_mass(Double exact_mass) {
        this.exact_mass = exact_mass;
    }

    public String getCanonical_SMILES() {
        return canonical_SMILES;
    }

    public void setCanonical_SMILES(String canonical_SMILES) {
        this.canonical_SMILES = canonical_SMILES;
    }

    public String getInChI() {
        return InChI;
    }

    public void setInChI(String inChI) {
        InChI = inChI;
    }

    public Integer getNum_atoms() {
        return num_atoms;
    }

    public void setNum_atoms(Integer num_atoms) {
        this.num_atoms = num_atoms;
    }

    public Integer getNum_bonds() {
        return num_bonds;
    }

    public void setNum_bonds(Integer num_bonds) {
        this.num_bonds = num_bonds;
    }

    public Integer getNum_residues() {
        return num_residues;
    }

    public void setNum_residues(Integer num_residues) {
        this.num_residues = num_residues;
    }

    public Integer getNum_rotors() {
        return num_rotors;
    }

    public void setNum_rotors(Integer num_rotors) {
        this.num_rotors = num_rotors;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getNum_rings() {
        return num_rings;
    }

    public void setNum_rings(Integer num_rings) {
        this.num_rings = num_rings;
    }

    public Double getLogP() {
        return logP;
    }

    public void setLogP(Double logP) {
        this.logP = logP;
    }

    public Double getPSA() {
        return PSA;
    }

    public void setPSA(Double PSA) {
        this.PSA = PSA;
    }

    public Double getMR() {
        return MR;
    }

    public void setMR(Double MR) {
        this.MR = MR;
    }

    public void addObrpData(ObprpService.ObPropResult obPropResult) {
        this.formula = obPropResult.getFormula();
        this.mol_weight = obPropResult.getMol_weight();
        this.exact_mass = obPropResult.getExact_mass();
        this.canonical_SMILES = obPropResult.getCanonical_SMILES();
        this.InChI = obPropResult.getInChI();
        this.num_atoms = obPropResult.getNum_atoms();
        this.num_bonds = obPropResult.getNum_bonds();
        this.num_residues = obPropResult.getNum_residues();
        this.num_rotors = obPropResult.getNum_rotors();
        this.sequence = obPropResult.getSequence();
        this.num_rings = obPropResult.getNum_rings();
        this.logP = obPropResult.getLogP();
        this.PSA = obPropResult.getPSA();
        this.MR = obPropResult.getMR();
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
