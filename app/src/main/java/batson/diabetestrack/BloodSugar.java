package batson.diabetestrack;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_blood_sugar")
public class BloodSugar {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private Integer ID;

    public void setID(Integer id) {
        this.ID = id;
    }

    @ColumnInfo(name = "blood_sugar_level")
    private int bsLevel;

    @ColumnInfo(name = "creation_date")
    private Date creationDate;

    public BloodSugar(int bsLevel, Date creationDate) {
        this.bsLevel = bsLevel;
        this.creationDate = creationDate;
    }

    public int getBsLevel() { return bsLevel; }

    public Date getCreationDate() { return creationDate; }

    public Integer getID() { return ID; }
}
