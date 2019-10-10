package cz.uhk.chemdb.bean.view;

import cz.uhk.chemdb.model.chemdb.repositories.LogRepository;
import cz.uhk.chemdb.model.chemdb.table.EventType;
import cz.uhk.chemdb.model.chemdb.table.Log;
import cz.uhk.chemdb.model.chemdb.table.LogSection;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class LogView {
    private List<Log> logs;
    @Inject
    private LogRepository logRepository;

    @PostConstruct
    public void init() {
        logs = logRepository.findAll();
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogRepository(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public List<String> getEventTypeList() {
        List<String> events = new ArrayList<>();
        for (EventType eventType : EventType.values()) {
            events.add(eventType.name());
        }
        return events;
    }

    public List<String> getEventLogSectionList() {
        List<String> logSections = new ArrayList<>();
        for (LogSection logSection : LogSection.values()) {
            logSections.add(logSection.name());
        }
        return logSections;
    }

    public void revert(Log log) {
        System.out.println(log);
    }
}
