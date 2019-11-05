package cz.uhk.chemdb.model;

import cz.uhk.chemdb.model.chemdb.builder.UserBuilder;
import cz.uhk.chemdb.model.chemdb.repositories.OwnerRepositiry;
import cz.uhk.chemdb.model.chemdb.repositories.TargetRepository;
import cz.uhk.chemdb.model.chemdb.repositories.UserRepository;
import cz.uhk.chemdb.model.chemdb.table.Owner;
import cz.uhk.chemdb.model.chemdb.table.Target;
import cz.uhk.chemdb.model.chemdb.table.User;
import cz.uhk.chemdb.model.security.PasswordHash;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class DefaultDataGenerator {
    @Inject
    UserRepository userRepository;
    @Inject
    OwnerRepositiry ownerRepositiry;
    @Inject
    TargetRepository targetRepository;
    @Inject
    PasswordHash passwordHash;


    public void generateDummyData() {
        System.out.println("GENERATING DATA ....");
        generateTestUsers();
        generateOwners();
        saveTargets();
    }

    @Transactional
    private void generateTestUsers() {
        User admin = UserBuilder.createUserBuilder("admin", passwordHash.getPasswordHash(
                "admin", "admin")).setName("Administrator").setAdmin(true)
                .setSuperAdmin(true).setToken("demo").build();
        userRepository.saveAndFlush(admin);
    }

    @Transactional
    private void generateOwners() {
        ownerRepositiry.save(new Owner("UHK"));
        ownerRepositiry.save(new Owner("FVZ"));
        ownerRepositiry.save(new Owner("FAF"));
        ownerRepositiry.save(new Owner("FNHK"));
        ownerRepositiry.save(new Owner("NUDZ"));
    }

    @Transactional
    public void saveTargets() {
        String[] names = new String[]{"2,7 DCDHDF",
                "Abeta 1-40 agggregation ThT",
                "Abeta 1-42 agggregation ThT",
                "AChE inhibition",
                "BACE1 inhibition",
                "BChE inhibition",
                "CMC",
                "CypD inhibition",
                "cytotoxicity",
                "DHE",
                "DPPH",
                "GLU2ND",
                "GLU3NA",
                "GLU3NB",
                "GLUN2A",
                "GLUN2B",
                "GLUN2C",
                "HSD-1 inhibition",
                "HSD-10 inhibition",
                "HSD-7 inhibition",
                "kinetics reAChE cyclosarin",
                "kinetics reAChE dichlorvos",
                "kinetics reAChE ethyl-paraoxon",
                "kinetics reAChE methyl-paraoxon",
                "kinetics reAChE sarin",
                "kinetics reAChE soman",
                "kinetics reAChE tabun",
                "kinetics reAChE VX",
                "kinetics reBuChE cyclosarin",
                "kinetics reBuChE dichlorvos",
                "kinetics reBuChE ethyl-paraoxon",
                "kinetics reBuChE methyl-paraoxon",
                "kinetics reBuChE sarin",
                "kinetics reBuChE soman",
                "kinetics reBuChE tabun",
                "kinetics reBuChE VX",
                "M1 mChR CHO-M1",
                "MAO-A inhibition",
                "MAO-B inhibition",
                "MBC",
                "MBEC",
                "MIC 24h",
                "MIC 48h",
                "MIC50",
                "MIC99",
                "NAChR muscular TE671",
                "NAChR neuronal",
                "NMDA",
                "ORX2",
                "POP inhibition",
                "reAChE cyclosarin (10 µM)",
                "reAChE cyclosarin (100 µM)",
                "reAChE dichlorvos (10 µM)",
                "reAChE dichlorvos (100 µM)",
                "reAChE ethyl-paraoxon (10 µM)",
                "reAChE ethyl-paraoxon (100 µM)",
                "reAChE methyl-paraoxon (10 µM)",
                "reAChE methyl-paraoxon (100 µM)",
                "reAChE sarin (10 µM)",
                "reAChE sarin (100 µM)",
                "reAChE soman (10 µM)",
                "reAChE soman (100 µM)",
                "reAChE tabun (10 µM)",
                "reAChE tabun (100 µM)",
                "reAChE VX (10 µM)",
                "reAChE VX (100 µM)",
                "reBChE cyclosarin (10 µM)",
                "reBChE cyclosarin (100 µM)",
                "reBChE dichlorvos (10 µM)",
                "reBChE dichlorvos (100 µM)",
                "reBChE ethyl-paraoxon (10 µM)",
                "reBChE ethyl-paraoxon (100 µM)",
                "reBChE methyl-paraoxon (10 µM)",
                "reBChE methyl-paraoxon (100 µM)",
                "reBChE sarin (10 µM)",
                "reBChE sarin (100 µM)",
                "reBChE soman (10 µM)",
                "reBChE soman (100 µM)",
                "reBChE tabun (10 µM)",
                "reBChE tabun (100 µM)",
                "reBChE VX (10 µM)",
                "reBChE VX (100 µM)",
                "solubitily DMSO 1%/PBS",
                "solubitily DMSO 1%/water",
                "solubitily DMSO 10%/PBS",
                "solubitily DMSO 10%/water",
                "solubitily DMSO 5%/PBS",
                "solubitily DMSO 5%/PBS",
                "solubitily DMSO 5%/water",
                "solubitily DMSO 5%/water",
                "solubitily MeOH 1%/PBS",
                "solubitily MeOH 1%/water",
                "solubitily MeOH 10%/PBS",
                "solubitily MeOH 10%/water",
                "solubitily MeOH 5%/PBS",
                "solubitily MeOH 5%/PBS",
                "solubitily PBS",
                "solubitily water",
                "tau aggregation ThT",
                "TBARS",
                "TDIC50",
                "TLR-4",
                "transport monolayer",
                "transport PAMPA",
                "toxicity",
                "pharmacokinetics"};

        for (String name : names) {
            Target t = new Target();
            t.setName(name);
            targetRepository.save(t);
        }
    }


}
