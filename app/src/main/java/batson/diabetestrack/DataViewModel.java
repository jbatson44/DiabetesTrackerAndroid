package batson.diabetestrack;

import android.app.Application;

import java.time.LocalDateTime;
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

    LiveData<List<BloodSugar>> getBloodSugarByDates(LocalDateTime beginTime, LocalDateTime endTime)
    {
        return diabetesRepository.getBloodSugarByDates(beginTime, endTime);
    }
    LiveData<List<Carb>> getCarbByDates(LocalDateTime beginTime, LocalDateTime endTime)
    {
        return diabetesRepository.getCarbByDates(beginTime, endTime);
    }
    LiveData<List<Insulin>> getInsulinByDates(LocalDateTime beginTime, LocalDateTime endTime)
    {
        return diabetesRepository.getInsulinByDates(beginTime, endTime);
    }

    public void insertBloodSugar(BloodSugar bloodSugar) { diabetesRepository.addBloodSugar(bloodSugar); }
    public void insertCarb(Carb carb) { diabetesRepository.addCarb(carb); }
    public void insertInsulin(Insulin insulin) { diabetesRepository.addInsulin(insulin); }

    public void deleteBloodSugar(BloodSugar bloodSugar) { diabetesRepository.DeleteBloodSugar(bloodSugar); }

    void deleteBloodSugarById(int id) { diabetesRepository.deleteBloodSugarById(id); }
    void deleteCarbById(int id) { diabetesRepository.deleteCarbById(id); }
    void deleteInsulinById(int id) { diabetesRepository.deleteInsulinById(id); }
}
