package batson.diabetestrack;

import android.app.Application;

import java.util.Calendar;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DataViewModel extends AndroidViewModel {
    private DiabetesRepository diabetesRepository;

    private final LiveData<List<BloodSugar>> bloodSugarList;

    public DataViewModel (Application application) {
        super(application);
        diabetesRepository = new DiabetesRepository(application);

        bloodSugarList = diabetesRepository.getAllBloodSugar();
    }

    LiveData<List<BloodSugar>> getBloodSugar() { return bloodSugarList; }

    public void insert(BloodSugar bloodSugar) { diabetesRepository.addBloodSugar(bloodSugar); }
}
