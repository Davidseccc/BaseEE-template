package cz.uhk.chemdb;

import cz.uhk.chemdb.model.DefaultDataGenerator;
import cz.uhk.chemdb.model.chemdb.repositories.OwnerRepositiry;
import cz.uhk.chemdb.model.chemdb.repositories.TargetRepository;
import cz.uhk.chemdb.model.chemdb.repositories.UserRepository;
import cz.uhk.chemdb.model.chemdb.table.Target;
import org.flywaydb.core.Flyway;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Application-startup tasks. Executed before the application itself is fully enabled.
 */
@Startup
@Singleton
public class ApplicationStartup {

    @Resource(lookup = "java:/chemDB/db")
    private DataSource dataSource;

    @Inject
    private DefaultDataGenerator defaultDataGenerator;

    @Inject
    OwnerRepositiry ownerRepositiry;

    @Inject
    TargetRepository targetRepository;


    @Inject
    private UserRepository userRepository;

    public static boolean isProduction() {
        String debug = System.getProperty("debug");
        return debug == null || !debug.equals("true");
    }

    @PostConstruct
    @TransactionAttribute(TransactionAttributeType.NEVER)
    private void onStartup() {
        migrateDatabase();
        insertDummyData();
        saveTargets();

        for (Handler handler : Logger.getGlobal().getHandlers()) {
            Logger.getGlobal().removeHandler(handler);
        }

        for (Handler handler : Logger.getAnonymousLogger().getHandlers()) {
            Logger.getAnonymousLogger().removeHandler(handler);
        }

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);

        Logger.getGlobal().addHandler(consoleHandler);
        Logger.getGlobal().setUseParentHandlers(false);
        Logger.getAnonymousLogger().addHandler(consoleHandler);
        Logger.getAnonymousLogger().setUseParentHandlers(false);
    }

    /**
     * Performs FlyWay database migrations
     */
    private void migrateDatabase() {
        if (dataSource == null) {
            throw new EJBException(
                    "No datasource found. Database migrations can not be executed.");
        }

        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setValidateOnMigrate(false);
        flyway.migrate();
    }

    /**
     * Inserts dummy data into database
     */
    private void insertDummyData() {
        if (userRepository.count() == 0) {
            defaultDataGenerator.generateDummyData();
        }
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
