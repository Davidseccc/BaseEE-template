package cz.uhk.chemdb.enums;

import javax.inject.Named;
import java.io.Serializable;

@Named
public enum SearchField implements Serializable {

    K(1, "k", DataType.INTEGER),
    Smiles(2, "smiles", DataType.STRING),
    OriginalCodename(3, "originalCodename", DataType.STRING),
    ION(4, "ion", DataType.STRING),
    MW(5, "mw", DataType.DOUBLE),
    NOTES(6, "notes", DataType.STRING),
    OWNER_name(7, "owner.name", DataType.STRING),

    MELTING_POINT_isOil(8, "meltingPoint.oil", DataType.BOOLEAN),
    MELTING_POINT_temperatureFrom(9, "meltingPoint.temperatureFrom", DataType.FLOAT),
    MELTING_POINT_temperatureTo(10, "meltingPoint.temperatureTo", DataType.FLOAT),

    DESCRIPTOR_formula(11, "descriptor.formula", DataType.STRING),
    //DESCRIPTOR_mw(12, "descriptor.mw", DataType.FLOAT),
    DESCRIPTOR_hdb(13, "descriptor.hdb", DataType.INTEGER),
    DESCRIPTOR_hba(14, "descriptor.hba", DataType.INTEGER),
    DESCRIPTOR_rb(15, "descriptor.rb", DataType.STRING),
    DESCRIPTOR_tpsa(16, "descriptor.tpsa", DataType.FLOAT),
    DESCRIPTOR_atoms(17, "descriptor.atoms", DataType.INTEGER),
    DESCRIPTOR_clogp(18, "descriptor.clogp", DataType.FLOAT),
    DESCRIPTOR_NMR(19, "descriptor.NMR", DataType.STRING),
    DESCRIPTOR_HRMS(20, "descriptor.HRMS", DataType.STRING),
    DESCRIPTOR_purity(20, "descriptor.purity", DataType.FLOAT),
    DESCRIPTOR_solubility(21, "descriptor.solubility", DataType.STRING),
    DESCRIPTOR_mol_weight(22, "descriptor.mol_weight", DataType.DOUBLE),
    DESCRIPTOR_exact_mass(23, "descriptor.exact_mass", DataType.DOUBLE),
    DESCRIPTOR_canonical_SMILES(24, "descriptor.exact_mass", DataType.DOUBLE),
    DESCRIPTOR_InChI(25, "descriptor.InChI", DataType.STRING),
    DESCRIPTOR_num_atoms(26, "descriptor.num_atoms", DataType.INTEGER),
    DESCRIPTOR_num_bonds(26, "descriptor.num_bonds", DataType.INTEGER),
    DESCRIPTOR_num_residues(26, "descriptor.num_residues", DataType.INTEGER),
    DESCRIPTOR_num_rotors(27, "descriptor.num_rotors", DataType.INTEGER),
    DESCRIPTOR_sequence(28, "descriptor.sequence", DataType.INTEGER),
    DESCRIPTOR_num_rings(29, "descriptor.num_rings", DataType.INTEGER),
    DESCRIPTOR_logP(30, "descriptor.logP", DataType.DOUBLE),
    DESCRIPTOR_PSA(31, "descriptor.PSA", DataType.DOUBLE),
    DESCRIPTOR_MR(32, "descriptor.MR", DataType.DOUBLE),

    //SYNONYMUM_name(33, "synonyms.name", DataType.STRING),
    //SYNONYMUM_note(34, "synonyms.note", DataType.DOUBLE),

    INVITRO_value(35, "invitro.value", DataType.DOUBLE),
    INVITRO_value_text(36, "invitro.value_text", DataType.STRING),
    //INVITRO_target(37, "invitro.target.name", DataType.STRING), //TODO: zkusit
    //INVITRO_organism(38, "invitro.organism.name", DataType.STRING), //TODO: zkusit
    INVITRO_conditions(39, "invitro.conditions", DataType.STRING),
    INVITRO_citation(40, "invitro.citation", DataType.STRING),
    INVITRO_doi(41, "invitro.doi", DataType.STRING),
    INVITRO_note(42, "invitro.note", DataType.STRING),
    //INVITRO_errorType(43, "invitro.errorType", DataType.STRING), //TODO: zkusit

    ATTRIBUTES_name(43, "attributes.key", DataType.STRING),
    ATTRIBUTES_value(43, "attributes.value", DataType.STRING);





    String attributeName;
    int id;
    DataType dataType;

    SearchField(int id, String attributeName, DataType dataType) {
        this.id = id;
        this.attributeName = attributeName;
        this.dataType = dataType;

    }

    public static SearchField findByAttributeName(String name) {
        for (SearchField type : SearchField.values()) {
            if (type.attributeName.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return attributeName;
    }

    public enum DataType {
        STRING(),
        INTEGER(),
        BOOLEAN(),
        DOUBLE(),
        FLOAT()
    }
}
