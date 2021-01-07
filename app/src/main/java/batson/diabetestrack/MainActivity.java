package batson.diabetestrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private DataViewModel dataViewModel;
    private Date chosenDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chosenDate = Calendar.getInstance().getTime();
        DiabetesDatabase db = Room.databaseBuilder(getApplicationContext(),
                DiabetesDatabase.class, "diabetes-database").build();

        TextView date = findViewById(R.id.chosenDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yy");
        date.setText(dateFormat.format(Calendar.getInstance().getTime()));

        TextView time = findViewById(R.id.bloodSugarTime);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        time.setText(timeFormat.format(Calendar.getInstance().getTime()));

        time.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int hour = cldr.get(Calendar.HOUR_OF_DAY);
            int minutes = cldr.get(Calendar.MINUTE);
            // time picker dialog
            TimePickerDialog picker = new TimePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                    (tp, sHour, sMinute) -> {
                        String amPm = "AM";
                        String hours = String.valueOf(sHour);
                        String minutes1 = String.valueOf(sMinute);

                        if (sHour > 12)
                        {
                            amPm = "PM";
                            hours = String.valueOf(sHour - 12);
                        }

                        if (sMinute < 10)
                        {
                            minutes1 = "0" + sMinute;
                        }

                        time.setText(hours + ":" + minutes1 + " " + amPm);
                    }, hour, minutes, false);

            picker.show();
        });

        Button addBloodSugar = findViewById(R.id.addBloodSugar);
        addBloodSugar.setOnClickListener(v -> {
            /*
            EditText bsInput = findViewById(R.id.bloodSugarInput);
            TextView bsTime = findViewById(R.id.bloodSugarTime);
            int bsLevel = Integer.parseInt(bsInput.getText().toString());
            Date bsDate = chosenDate;
            bsDate.setHours(Integer.parseInt(bsTime.getText().toString().split(":")[0]));
            bsDate.setMinutes(Integer.parseInt(bsTime.getText().toString().split(":")[1]));
            BloodSugar bloodSugar = new BloodSugar();
            bloodSugar.bsLevel = bsLevel;
            bloodSugar.creationDate = bsDate;
            dataViewModel.insert(bloodSugar);
            */
        });
    }


}