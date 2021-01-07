package batson.diabetestrack;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {BloodSugar.class, Carb.class, Insulin.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class DiabetesDatabase extends RoomDatabase {
    public abstract DiabetesDao diabetesDao();
}