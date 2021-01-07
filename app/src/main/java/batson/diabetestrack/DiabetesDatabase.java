package batson.diabetestrack;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {BloodSugar.class, Carb.class, Insulin.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class DiabetesDatabase extends RoomDatabase {
    public abstract DiabetesDao diabetesDao();

    private static volatile DiabetesDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static DiabetesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DiabetesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DiabetesDatabase.class, "diabetes_database").build();
                }
            }
        }
        return INSTANCE;
    }
}