package cz.uhk.chemdb.utils;

import javax.ejb.Singleton;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Singleton
public class ObprpService {


    public ObPropResult call(String smile) {
        int hash = smile.hashCode();
        String cmd = String.format(String.format("echo \"%s\" > out.smi && obprop out.smi && rm out.smi", smile));
        ProcessBuilder builder = new ProcessBuilder(
                "bash", "-c", cmd);
        builder.redirectErrorStream(true);
        Process p = null;

        try {
            p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line + '\n');
            }
            p.destroy();
            return new ObPropResult(sb.toString());
        } catch (Exception e) {
            e.getStackTrace();
            e.printStackTrace();
        } finally {
            if (p != null) p.destroy();
        }
        return null;
    }

    public class ObPropResult {
        public static final String END = "$$$$";
        String name;
        String formula;
        double mol_weight;
        double exact_mass;
        String canonical_SMILES;
        String InChI;
        Integer num_atoms;
        Integer num_bonds;
        Integer num_residues;
        Integer num_rotors;
        Integer sequence;
        Integer num_rings;
        double logP;
        double PSA;
        double MR;
        private int endPos = Integer.MAX_VALUE;

        public ObPropResult(String s) {
            endPos = s.indexOf(END);
            if (endPos == -1) {
                System.out.println("End char not found");
                return;
            }
            this.name = findInString("name", s);
            this.formula = findInString("formula", s);
            this.mol_weight = findInString("mol_weight", s).equals("-") ? null : Double.parseDouble(findInString("mol_weight", s));
            this.exact_mass = findInString("exact_mass", s).equals("-") ? null : Double.parseDouble(findInString("exact_mass", s));
            this.canonical_SMILES = findInString("canonical_SMILES", s);
            this.num_atoms = findInString("num_atoms", s).equals("-") ? null : Integer.parseInt(findInString("num_atoms", s));
            this.num_bonds = findInString("num_bonds", s).equals("-") ? null : Integer.parseInt(findInString("num_bonds", s));
            this.num_residues = findInString("num_residues", s).equals("-") ? null : Integer.parseInt(findInString("num_residues", s));
            this.num_rotors = findInString("num_rotors", s).equals("-") ? null : Integer.parseInt(findInString("num_rotors", s));
            this.sequence = findInString("sequence", s).equals("-") ? null : Integer.parseInt(findInString("sequence", s));
            this.num_rings = findInString("num_rings", s).equals("-") ? null : Integer.parseInt(findInString("num_rings", s));
            this.logP = findInString("logP", s).equals("-") ? null : Double.parseDouble(findInString("logP", s));
            this.PSA = findInString("PSA", s).equals("-") ? null : Double.parseDouble(findInString("PSA", s));
            this.MR = findInString("MR", s).equals("-") ? null : Double.parseDouble(findInString("MR", s));
        }

        private String findInString(String elementName, String s) {
            return (s.substring(s.indexOf(elementName) + elementName.length(),
                    Math.min(s.indexOf("\n", s.indexOf(elementName) + elementName.length()), this.endPos))).trim();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFormula() {
            return formula;
        }

        public void setFormula(String formula) {
            this.formula = formula;
        }

        public double getMol_weight() {
            return mol_weight;
        }

        public void setMol_weight(double mol_weight) {
            this.mol_weight = mol_weight;
        }

        public double getExact_mass() {
            return exact_mass;
        }

        public void setExact_mass(double exact_mass) {
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

        public double getLogP() {
            return logP;
        }

        public void setLogP(double logP) {
            this.logP = logP;
        }

        public double getPSA() {
            return PSA;
        }

        public void setPSA(double PSA) {
            this.PSA = PSA;
        }

        public double getMR() {
            return MR;
        }

        public void setMR(double MR) {
            this.MR = MR;
        }

        @Override
        public String toString() {
            return "ObPropResult{" +
                    "name='" + name + '\'' +
                    ", formula='" + formula + '\'' +
                    ", mol_weight=" + mol_weight +
                    ", exact_mass=" + exact_mass +
                    ", canonical_SMILES='" + canonical_SMILES + '\'' +
                    ", InChI='" + InChI + '\'' +
                    ", num_atoms=" + num_atoms +
                    ", num_bonds=" + num_bonds +
                    ", num_residues=" + num_residues +
                    ", num_rotors=" + num_rotors +
                    ", sequence=" + sequence +
                    ", num_rings=" + num_rings +
                    ", logP=" + logP +
                    ", PSA=" + PSA +
                    ", MR=" + MR +
                    '}';
        }
    }

    public class StringWithIndex {
        String string;
        int index;

        public StringWithIndex(String string, int index) {
            this.string = string;
            this.index = index;
        }

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }


}
