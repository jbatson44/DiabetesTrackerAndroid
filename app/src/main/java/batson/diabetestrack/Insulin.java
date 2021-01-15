package batson.diabetestrack;

import java.time.LocalDateTime;
import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_insulin")
public class Insulin extends DataItem {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private Integer ID;

    public void setID(Integer id) {
        this.ID = id;
    }

    @ColumnInfo(name = "units")
    private int units;

    @ColumnInfo(name = "creation_date")
    private LocalDateTime creationDate;

    public Insulin(int units, LocalDateTime creationDate) {
        this.units = units;
        this.creationDate = creationDate;
    }

    public int getUnits() { return units; }

    public LocalDateTime getCreationDate() { return creationDate; }

    public Integer getID() { return ID; }
}