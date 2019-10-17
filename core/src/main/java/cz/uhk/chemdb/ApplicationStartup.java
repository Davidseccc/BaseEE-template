package cz.uhk.chemdb;

import cz.uhk.chemdb.model.DefaultDataGenerator;
import cz.uhk.chemdb.model.chemdb.repositories.OwnerRepositiry;
import cz.uhk.chemdb.model.chemdb.repositories.UserRepository;
import org.flywaydb.core.Flyway;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.sql.DataSource;
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
    private UserRepository userRepository;

    public static boolean isProduction() {
        String debug = System.getProperty("debug");
        return debug == null || !debug.equals("true");
    }

    @PostConstruct
    @TransactionAttribute(TransactionAttributeType.NEVER)
    private void onStartup() {
        //ObprpService.ObPropResult res = obprpService.call("c1cc(cc[n+]1CC=CC[n+]1ccc(cc1)C(=O)OCC)C=NO");
        migrateDatabase();
        insertDummyData();

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

}
