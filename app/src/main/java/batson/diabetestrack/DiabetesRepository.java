package batson.diabetestrack;

import android.app.Application;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;

public class DiabetesRepository {
    private DiabetesDao diabetesDao;
    private LiveData<List<BloodSugar>> bloodSugar;

    DiabetesRepository(Application application) {
        DiabetesDatabase db = DiabetesDatabase.getDatabase(application);
        diabetesDao = db.diabetesDao();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        bloodSugar = diabetesDao.getBloodSugar(cal.getTime(), Calendar.getInstance().getTime());
    }

    LiveData<List<BloodSugar>> getBloodSugarByDate(Date beginDate, Date endDate)
    {
        bloodSugar = diabetesDao.getBloodSugar(beginDate, endDate);
        return bloodSugar;
    }

    void addBloodSugar(BloodSugar bloodSugar) {
        DiabetesDatabase.databaseWriteExecutor.execute(() -> {
            diabetesDao.insertBloodSugar(bloodSugar);
        });
    }
}
