package batson.diabetestrack;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_insulin")
public class Insulin {
    @PrimaryKey
    public int ID;

    @ColumnInfo(name = "units")
    public int units;

    @ColumnInfo(name = "creation_date")
    public Date creationDate;
}
