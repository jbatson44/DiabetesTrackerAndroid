package batson.diabetestrack;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface DiabetesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertBloodSugar(BloodSugar bloodSugar);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCarb(Carb carb);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertInsulin(Insulin insulin);



    @Query("SELECT * FROM tbl_blood_sugar")
    LiveData<List<BloodSugar>> getAllBloodSugar();
    @Query("SELECT * FROM tbl_carb")
    LiveData<List<Carb>> getAllCarb();
    @Query("SELECT * FROM tbl_insulin")
    LiveData<List<Insulin>> getAllInsulin();

    @Delete
    void deleteBloodSugar(BloodSugar bloodSugar);

    @Query("DELETE FROM tbl_blood_sugar")
    void deleteAll();

    @Query("SELECT * FROM tbl_blood_sugar WHERE creation_date >= :beginTime AND " +
            "creation_date <= :endTime ORDER BY creation_date ASC")
    LiveData<List<BloodSugar>> getBloodSugar(LocalDateTime beginTime, LocalDateTime endTime);

    @Query("SELECT * FROM tbl_carb WHERE creation_date >= :beginTime AND " +
            "creation_date <= :endTime ORDER BY creation_date ASC")
    LiveData<List<Carb>> getCarb(LocalDateTime beginTime, LocalDateTime endTime);

    @Query("SELECT * FROM tbl_insulin WHERE creation_date >= :beginTime AND " +
            "creation_date <= :endTime ORDER BY creation_date ASC")
    LiveData<List<Insulin>> getInsulin(LocalDateTime beginTime, LocalDateTime endTime);


    @Query("DELETE FROM tbl_carb WHERE ID = :id")
    void deleteCarbById(int id);
    @Query("DELETE FROM tbl_blood_sugar WHERE ID = :id")
    void deleteBloodSugarById(int id);
    @Query("DELETE FROM tbl_insulin WHERE ID = :id")
    void deleteInsulinById(int id);

    @Delete
    void deleteCarb(Carb carb);

    @Delete
    void deleteInsulin(Insulin insulin);
}
