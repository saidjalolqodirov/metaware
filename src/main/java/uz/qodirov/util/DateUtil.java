package uz.qodirov.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static String epochToDate(Long time) {
        if (time == null) {
            return null;
        }
        ZonedDateTime zonedDateTime = Instant.ofEpochMilli(time).atZone(ZoneId.of("Asia/Tashkent"));

        return zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public static Long dateToEpoch(String date) {
        if (date == null) {
            return null;
        }
        OffsetDateTime odt = OffsetDateTime.parse(date);
        return odt.toEpochSecond() * 1000;
    }

    public static String getTodayEndInIsoFormat() {
        ZoneId zone = ZoneId.of("Asia/Tashkent");
        ZonedDateTime endOfDay = LocalDate.now(zone)
                .atTime(LocalTime.of(23, 59, 59))
                .atZone(zone);
        return endOfDay.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}