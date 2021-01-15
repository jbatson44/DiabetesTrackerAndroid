package batson.diabetestrack;

import java.time.LocalDateTime;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_blood_sugar")
public class BloodSugar extends DataItem {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private Integer ID;

    public void setID(Integer id) {
        this.ID = id;
    }

    @ColumnInfo(name = "blood_sugar_level")
    private int bsLevel;

    @ColumnInfo(name = "creation_date")
    private LocalDateTime creationDate;

    public BloodSugar(int bsLevel, LocalDateTime creationDate) {
        this.bsLevel = bsLevel;
        this.creationDate = creationDate;
    }

    public int getBsLevel() { return bsLevel; }

    public LocalDateTime getCreationDate() { return creationDate; }

    public Integer getID() { return ID; }
}
