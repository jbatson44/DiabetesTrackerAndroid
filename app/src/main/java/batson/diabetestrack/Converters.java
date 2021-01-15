package batson.diabetestrack;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Date;
import java.util.TimeZone;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static LocalDateTime fromTimestamp(Long value) {
        return value == null ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(value),
                TimeZone.getDefault().toZoneId());
    }
    @TypeConverter
    public static Long dateToTimestamp(LocalDateTime date) {
        return date == null ? null : date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}