package batson.diabetestrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private DataViewModel dataViewModel;
    private Date chosenDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chosenDate = Calendar.getInstance().getTime();



        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final DataListAdapter adapter = new DataListAdapter(new DataListAdapter.DataDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        dataViewModel.getBloodSugar().observe(this, data -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(data);
        });

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

                        if (sHour == 0)
                        {
                            hours = String.valueOf(12);
                        }
                        time.setText(hours + ":" + minutes1 + " " + amPm);
                    }, hour, minutes, false);

            picker.show();
        });

        Button addBloodSugar = findViewById(R.id.addBloodSugar);
        addBloodSugar.setOnClickListener(v -> {

            EditText bsInput = findViewById(R.id.bloodSugarInput);
            TextView bsTime = findViewById(R.id.bloodSugarTime);
            int bsLevel = Integer.parseInt(bsInput.getText().toString());
            Date bsDate = chosenDate;

            String amPm = bsTime.getText().toString().split(":")[1].split(" ")[1];

            int hours = Integer.parseInt(bsTime.getText().toString().split(":")[0]);
            int minutes = Integer.parseInt(bsTime.getText().toString().split(":")[1].split(" ")[0]);
            Log.d("activity", "ampm:" + amPm + "!");
            Log.d("activity", "hours: " + hours);
            if (amPm.equals("PM")) {
                hours += 12;
                Log.d("activity", "hours: " + hours);
            }
            else if (hours == 12) {
                hours = 0;
            }
            Log.d("activity", "hours: " + hours);

            bsDate.setHours(hours);
            bsDate.setMinutes(minutes);

            BloodSugar bloodSugar = new BloodSugar(bsLevel, bsDate);

            dataViewModel.insert(bloodSugar);

        });
    }


}