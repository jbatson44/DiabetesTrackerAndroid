package batson.diabetestrack;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {BloodSugar.class}, version = 1, exportSchema = false)
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
                            DiabetesDatabase.class, "diabetes_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                DiabetesDao dao = INSTANCE.diabetesDao();
                dao.deleteAll();

                BloodSugar bloodSugar = new BloodSugar(98, Calendar.getInstance().getTime());
                dao.insertBloodSugar(bloodSugar);
            });
        }
    };
}