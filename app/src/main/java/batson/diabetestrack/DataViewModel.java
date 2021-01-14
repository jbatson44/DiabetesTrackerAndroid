package batson.diabetestrack;

import android.app.Application;

import java.util.Calendar;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DataViewModel extends AndroidViewModel {
    private DiabetesRepository diabetesRepository;

    private final LiveData<List<BloodSugar>> bloodSugarList;
    private final LiveData<List<Insulin>> insulinList;
    private final LiveData<List<Carb>> carbList;

    public DataViewModel (Application application) {
        super(application);
        diabetesRepository = new DiabetesRepository(application);

        bloodSugarList = diabetesRepository.getAllBloodSugar();
        carbList = diabetesRepository.getAllCarb();
        insulinList = diabetesRepository.getAllInsulin();
    }

    LiveData<List<BloodSugar>> getBloodSugar() { return bloodSugarList; }
    LiveData<List<Carb>> getCarbs() { return carbList; }
    LiveData<List<Insulin>> getInsulin() { return insulinList; }

    public void insertBloodSugar(BloodSugar bloodSugar) { diabetesRepository.addBloodSugar(bloodSugar); }
    public void insertCarb(Carb carb) { diabetesRepository.addCarb(carb); }
    public void insertInsulin(Insulin insulin) { diabetesRepository.addInsulin(insulin); }
}
