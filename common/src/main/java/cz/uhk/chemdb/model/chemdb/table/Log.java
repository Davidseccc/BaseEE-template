package cz.uhk.chemdb.model.chemdb.table;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "log")
public class Log extends BaseModel {
    @Enumerated(EnumType.STRING)
    EventType eventType;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private LogSection logSection;

    private String description;

    private LocalDateTime timestamp;

    public Log() {

    }

    public Log(EventType eventType, User user, LogSection logSection, String description) {
        this.eventType = eventType;
        this.user = user;
        this.logSection = logSection;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LogSection getLogSection() {
        return logSection;
    }

    public void setLogSection(LogSection logSection) {
        this.logSection = logSection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return id + "-" + eventType + " in " + logSection;
    }
}
