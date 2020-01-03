package cz.uhk.chemdb.utils;

import javax.ejb.Singleton;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

@Singleton
@Named
public class DateFormats implements Serializable {

    private final DateTimeFormatter dateTimeSecFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    private final DateTimeFormatter fitbitDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter fitbitTimeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final DateTimeFormatter fitbitDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static Calendar localDateTimeToCalendar(LocalDateTime localDateTime) {
        Calendar calendar = Calendar.getInstance(StringUtils.CZECH_LOCALE);
        calendar.clear();
        calendar.set(localDateTime.getYear(), localDateTime.getMonthValue() - 1, localDateTime.getDayOfMonth(),
                localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());
        return calendar;
    }

    public DateTimeFormatter getDateTimeSecFormat() {
        return dateTimeSecFormat;
    }

    public DateTimeFormatter getDateTimeFormat() {
        return dateTimeFormat;
    }

    public DateTimeFormatter getDateFormat() {
        return dateFormat;
    }

    public DateTimeFormatter getTimeFormat() {
        return timeFormat;
    }

    public DateTimeFormatter getFitbitDateFormat() {
        return fitbitDateFormat;
    }

    public DateTimeFormatter getFitbitTimeFormat() {
        return fitbitTimeFormat;
    }

    public DateTimeFormatter getFitbitDateTimeFormat() {
        return DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    }

    public DateTimeFormatter getFitbitDateTimeformat() {
        return fitbitDateTimeFormat;
    }

    public LocalDateTime toLocalDateTime(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        TimeZone tz = calendar.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }
}
