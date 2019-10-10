package cz.uhk.chemdb.util;

import cz.uhk.chemdb.model.chemdb.repositories.LogRepository;
import cz.uhk.chemdb.model.chemdb.table.EventType;
import cz.uhk.chemdb.model.chemdb.table.Log;
import cz.uhk.chemdb.model.chemdb.table.LogSection;
import cz.uhk.chemdb.model.chemdb.table.User;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Singleton
public class LogUtils {
    @Inject
    LogRepository logRepository;

    @Transactional
    public void createAndSaveLog(EventType eventType, User user, LogSection logSection, String description) {
        Log log = new Log(eventType, user, logSection, description);
        logRepository.save(log);
    }

}
