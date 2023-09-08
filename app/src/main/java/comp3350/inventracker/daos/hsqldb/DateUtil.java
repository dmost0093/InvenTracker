package comp3350.inventracker.daos.hsqldb;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {
    public static LocalDate toLocalDate(Date date) {
        return (date == null)
            ? null
            : (date instanceof java.sql.Date)
                ? Instant.ofEpochMilli(date.getTime())
                         .atZone(ZoneId.systemDefault())
                         .toLocalDate()
                : date.toInstant()
                      .atZone(ZoneId.systemDefault())
                      .toLocalDate();
    }
}
