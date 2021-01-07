package batson.diabetestrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DiabetesDatabase db = Room.databaseBuilder(getApplicationContext(),
                DiabetesDatabase.class, "diabetes-database").build();

        TextView date = findViewById(R.id.chosenDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yy");
        date.setText(dateFormat.format(Calendar.getInstance().getTime()));
    }
}