package batson.diabetestrack;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_carb")
public class Carb {
    @PrimaryKey
    public int ID;

    @ColumnInfo(name = "grams")
    public int grams;

    @ColumnInfo(name = "creation_date")
    public Date creationDate;
}
