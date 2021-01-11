package batson.diabetestrack;

import android.app.Application;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;

public class DiabetesRepository {
    private DiabetesDao diabetesDao;
    private LiveData<List<BloodSugar>> bloodSugarList;

    DiabetesRepository(Application application) {
        DiabetesDatabase db = DiabetesDatabase.getDatabase(application);
        diabetesDao = db.diabetesDao();

        bloodSugarList = diabetesDao.getAllBloodSugar();
    }

    LiveData<List<BloodSugar>> getAllBloodSugar()
    {
        return bloodSugarList;
    }

    void addBloodSugar(BloodSugar bloodSugar) {
        DiabetesDatabase.databaseWriteExecutor.execute(() -> diabetesDao.insertBloodSugar(bloodSugar));
    }
}
