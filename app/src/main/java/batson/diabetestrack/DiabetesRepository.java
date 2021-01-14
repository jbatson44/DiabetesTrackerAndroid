package batson.diabetestrack;

import android.app.Application;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;

public class DiabetesRepository {
    private DiabetesDao diabetesDao;

    private LiveData<List<DataItem>> dataItemList;
    private LiveData<List<BloodSugar>> bloodSugarList;
    private LiveData<List<Carb>> carbList;
    private LiveData<List<Insulin>> insulinList;

    DiabetesRepository(Application application) {
        DiabetesDatabase db = DiabetesDatabase.getDatabase(application);
        diabetesDao = db.diabetesDao();

        bloodSugarList = diabetesDao.getAllBloodSugar();
        carbList = diabetesDao.getAllCarb();
        insulinList = diabetesDao.getAllInsulin();
    }

    LiveData<List<BloodSugar>> getAllBloodSugar()
    {
        return bloodSugarList;
    }
    LiveData<List<Carb>> getAllCarb()
    {
        return carbList;
    }
    LiveData<List<Insulin>> getAllInsulin()
    {
        return insulinList;
    }

    void addBloodSugar(BloodSugar bloodSugar) {
        DiabetesDatabase.databaseWriteExecutor.execute(() -> diabetesDao.insertBloodSugar(bloodSugar));
    }
    void addCarb(Carb carb) {
        DiabetesDatabase.databaseWriteExecutor.execute(() -> diabetesDao.insertCarb(carb));
    }
    void addInsulin(Insulin insulin) {
        DiabetesDatabase.databaseWriteExecutor.execute(() -> diabetesDao.insertInsulin(insulin));
    }
}
