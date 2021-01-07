package batson.diabetestrack;

import android.app.Application;

import java.util.Calendar;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DataViewModel extends AndroidViewModel {
    private DiabetesRepository diabetesRepository;

    private final LiveData<List<BloodSugar>> bloodSugar;

    public DataViewModel (Application application) {
        super(application);
        diabetesRepository = new DiabetesRepository(application);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        bloodSugar = diabetesRepository.getBloodSugarByDate(cal.getTime(), Calendar.getInstance().getTime());
    }

    LiveData<List<BloodSugar>> getBloodSugar() { return bloodSugar; }

    public void insert(BloodSugar bloodSugar) { diabetesRepository.addBloodSugar(bloodSugar); }
}
