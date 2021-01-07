package batson.diabetestrack;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_blood_sugar")
public class BloodSugar {
    @PrimaryKey
    public int ID;

    @ColumnInfo(name = "blood_sugar_level")
    public int bsLevel;

    @ColumnInfo(name = "creation_date")
    public Date creationDate;
}
