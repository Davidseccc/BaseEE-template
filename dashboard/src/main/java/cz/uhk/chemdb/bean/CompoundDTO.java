package cz.uhk.chemdb.bean;

import cz.uhk.chemdb.model.chemdb.table.Attribute;
import cz.uhk.chemdb.model.chemdb.table.Compound;
import cz.uhk.chemdb.model.chemdb.table.Invitro;
import cz.uhk.chemdb.model.chemdb.table.Synonymum;
import cz.uhk.chemdb.utils.PermissionAttribute;
import cz.uhk.chemdb.utils.PermissionRole;
import cz.uhk.chemdb.utils.StringUtils;

import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Named
public class CompoundDTO implements Serializable {

    public Map<String, String> compounds = new HashMap<>();
    public Map<String, Map<String, String>> targets = new HashMap<>();
    public Map<String, String> attributes = new HashMap<>();

    public static String synonymsToString(Set<Synonymum> synonyms) {
        String delimiter = ", ";
        Iterator<Synonymum> iter = synonyms.iterator();
        if (!iter.hasNext()) return "";
        StringBuilder buffer = new StringBuilder(iter.next().getName());
        while (iter.hasNext()) buffer.append(delimiter).append(iter.next().getName());
        return buffer.toString();
    }

    private static Map<String, String> invitroToString(Invitro invitro, PermissionRole userRole) {
        Map<String, String> out = new HashMap<>();
        out.put("Value", invitro.getformatedValue());
        out.put("Error", invitro.getErrorType().getName());
        if (!StringUtils.isEmpty(invitro.getValue_text())) {
            out.put("Error value", invitro.getValue_text());
        }
        return out;
    }

    public void compoundToDTO(Compound compound, UserManager userManager) {
        PermissionRole userRole = userManager.getUserRole();
        compounds.put("k", String.format("%s", compound.getK()));
        if (userManager.hasPermission(PermissionAttribute.compound_Smiles))
            compounds.put("smiles", compound.getSmiles());
        if (userManager.hasPermission(PermissionAttribute.compound_ION))
            compounds.put("ion", compound.getIon());
        if (userManager.hasPermission(PermissionAttribute.compound_MW))
            compounds.put("mw", String.format("%s", compound.getDescriptor().getMol_weight()));
        if (userManager.hasPermission(PermissionAttribute.OWNER_name))
            compounds.put("owner", compound.getOwner().getName());
        if (userManager.hasPermission(PermissionAttribute.MELTING_POINT_all))
            compounds.put("melting point", compound.getMeltingPoint().getStringValue());
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_formula))
            compounds.put("formula", compound.getDescriptor().getFormula());
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_hbd))
            compounds.put("hbd", String.format("%s", compound.getDescriptor().getHbd()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_hba))
            compounds.put("hba", String.format("%s", compound.getDescriptor().getHba()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_rb))
            compounds.put("rb", String.format("%s", compound.getDescriptor().getRb()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_tpsa))
            compounds.put("tpsa", String.format("%s", compound.getDescriptor().getTpsa()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_atoms))
            compounds.put("atoms", String.format("%s", compound.getDescriptor().getAtoms()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_clogp))
            compounds.put("clogp", String.format("%s", compound.getDescriptor().getClogp()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_NMR))
            compounds.put("NMR", String.format("%s", compound.getDescriptor().getNMR()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_HRMS))
            compounds.put("HRMS", String.format("%s", compound.getDescriptor().getHRMS()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_purity))
            compounds.put("purity", String.format("%s%s", compound.getDescriptor().getPurityOperator(), compound.getDescriptor().getPurity()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_solubility))
            compounds.put("solubility", String.format("%s", compound.getDescriptor().getSolubility()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_exact_mass))
            compounds.put("exact mass", String.format("%s", compound.getDescriptor().getExact_mass()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_canonical_SMILES))
            compounds.put("canonical_SMILES", String.format("%s", compound.getDescriptor().getCanonical_SMILES()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_InChI))
            compounds.put("InChI", String.format("%s", compound.getDescriptor().getInChI()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_num_atoms))
            compounds.put("num atoms", String.format("%s", compound.getDescriptor().getNum_atoms()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_num_bonds))
            compounds.put("num bonds", String.format("%s", compound.getDescriptor().getNum_bonds()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_num_residues))
            compounds.put("num residues", String.format("%s", compound.getDescriptor().getNum_residues()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_num_rotors))
            compounds.put("num rotors", String.format("%s", compound.getDescriptor().getNum_rotors()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_sequence))
            compounds.put("sequence", String.format("%s", compound.getDescriptor().getSequence()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_num_rings))
            compounds.put("num rings", String.format("%s", compound.getDescriptor().getNum_rings()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_logP))
            compounds.put("logP", String.format("%s", compound.getDescriptor().getLogP()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_PSA))
            compounds.put("PSA", String.format("%s", compound.getDescriptor().getPSA()));
        if (userManager.hasPermission(PermissionAttribute.DESCRIPTOR_MR))
            compounds.put("MR", String.format("%s", compound.getDescriptor().getMR()));
        if (userManager.hasPermission(PermissionAttribute.SYNONYMUM_name))
            compounds.put("Alias", String.format("%s", synonymsToString(compound.getSynonyms())));
        if (userManager.hasPermission(PermissionAttribute.compound_doi))
            compounds.put("DOI", String.format("%s", compound.getDoi().getDoi()));
        if (userManager.hasPermission(PermissionAttribute.INVITRO_all)) {
            for (Invitro invitro : compound.getInvitro()) {
                targets.put(invitro.getTarget().getName(), invitroToString(invitro, userRole));
            }
        }
        if (userManager.hasPermission(PermissionAttribute.ATTRIBUTES_all)) {
            for (Attribute attribute : compound.getAttributes()) {
                attributes.put(attribute.getKey(), attribute.getValue());
            }
        }
    }

    public Map<String, String> getCompounds() {
        return compounds;
    }

    public void setCompounds(Map<String, String> compounds) {
        this.compounds = compounds;
    }

    public Map<String, Map<String, String>> getTargets() {
        return targets;
    }

    public void setTargets(Map<String, Map<String, String>> targets) {
        this.targets = targets;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
