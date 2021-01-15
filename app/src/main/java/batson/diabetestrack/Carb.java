package batson.diabetestrack;

import java.time.LocalDateTime;
import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_carb")
public class Carb extends DataItem {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private Integer ID;

    public void setID(Integer id) {
        this.ID = id;
    }

    @ColumnInfo(name = "grams")
    private int grams;

    @ColumnInfo(name = "creation_date")
    private LocalDateTime creationDate;

    public Carb(int grams, LocalDateTime creationDate) {
        this.grams = grams;
        this.creationDate = creationDate;
    }

    public int getGrams() { return grams; }

    public LocalDateTime getCreationDate() { return creationDate; }

    public Integer getID() { return ID; }
}