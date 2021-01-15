package batson.diabetestrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private DataViewModel dataViewModel;
    private LocalDate chosenDate;
    private DataType currentData;
    private Button insulinButton;
    private Button bloodSugarButton;
    private Button carbButton;
    private Button addData;
    private Button incrementDate;
    private Button decrementDate;

    private TextView dataLabel;
    private TextView timeLabel;
    private TextView time;
    private TextView chosenDateText;
    private EditText dataInput;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Buttons
        insulinButton = findViewById(R.id.chooseInsulin);
        bloodSugarButton = findViewById(R.id.chooseBS);
        carbButton = findViewById(R.id.chooseCarb);
        incrementDate = findViewById(R.id.incrementDate);
        decrementDate = findViewById(R.id.decrementDate);

        // Add data button
        addData = findViewById(R.id.addData);

        // Label's and input fields
        dataLabel = findViewById(R.id.dataLabel);
        timeLabel = findViewById(R.id.timeLabel);
        time = findViewById(R.id.time);
        dataInput = findViewById(R.id.dataInput);

        // Recycler view
        recyclerView = findViewById(R.id.recyclerView);

        // By default we use the current date and Blood Sugar
        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        chosenDate = LocalDate.now();
        currentData = DataType.bloodSugar;
        SwitchInput(DataType.bloodSugar);


        chosenDateText = findViewById(R.id.chosenDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy", Locale.ENGLISH);
        chosenDateText.setText(formatter.format(LocalDate.now()));


        // Set time to current time
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
        time.setText(timeFormat.format(LocalDateTime.now()));

        incrementDate.setOnClickListener(v -> {
            IncrementDecrementDate(true);
        });
        decrementDate.setOnClickListener(v -> {
            IncrementDecrementDate(false);
        });

        insulinButton.setOnClickListener(v -> {
            SwitchInput(DataType.insulin);
        });
        bloodSugarButton.setOnClickListener(v -> {
            SwitchInput(DataType.bloodSugar);
        });
        carbButton.setOnClickListener(v -> {
            SwitchInput(DataType.carb);
        });

        time.setOnClickListener(v -> {
            String timeString = time.getText().toString();
            Log.d("activity", "time edit: " + timeString);
            System.out.println("time string: " + timeString);
            String hourString = timeString.split(":")[0];
            String minAmString = timeString.split(":")[1];
            String am = minAmString.split(" ")[1];

            final Calendar cldr = Calendar.getInstance();
            int hour = Integer.parseInt(hourString);//cldr.get(Calendar.HOUR_OF_DAY);//
            int minutes = Integer.parseInt(minAmString.split(" ")[0]);//cldr.get(Calendar.MINUTE);//Integer.parseInt(minAmString.split(" ")[0]);//
            if (am.equals("PM")) {
                hour += 12;
            }
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

        //Button addBloodSugar = findViewById(R.id.addData);
        addData.setOnClickListener(v -> {

            //EditText bsInput = findViewById(R.id.dataInput);
            //TextView bsTime = findViewById(R.id.time);
            int input = Integer.parseInt(dataInput.getText().toString());
            //Date bsDate = chosenDate;

            String amPm = time.getText().toString().split(":")[1].split(" ")[1];

            int hours = Integer.parseInt(time.getText().toString().split(":")[0]);
            int minutes = Integer.parseInt(time.getText().toString().split(":")[1].split(" ")[0]);
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

            LocalDateTime localDateTime = chosenDate.atTime(hours, minutes);
            //bsDate.setHours(hours);
            //bsDate.setMinutes(minutes);
            Log.d("activity", "Date: " + localDateTime.toString());
            if (currentData == DataType.bloodSugar) {
                BloodSugar bloodSugar = new BloodSugar(input, localDateTime);

                dataViewModel.insertBloodSugar(bloodSugar);
            }
            else if (currentData == DataType.carb) {
                Carb carb = new Carb(input, localDateTime);

                dataViewModel.insertCarb(carb);
            }
            else if (currentData == DataType.insulin) {
                Insulin insulin = new Insulin(input, localDateTime);

                dataViewModel.insertInsulin(insulin);
            }

        });
    }

    public void SwitchInput(DataType newDataType) {

        UpdateList(newDataType);
        if (newDataType == DataType.bloodSugar) {
            dataLabel.setText(R.string.blood_sugar);
            addData.setText(R.string.add_blood_sugar);

            currentData = DataType.bloodSugar;
        }
        else if (newDataType == DataType.carb) {
            dataLabel.setText(R.string.carb);
            addData.setText(R.string.add_carb);

            currentData = DataType.carb;
        }
        else if (newDataType == DataType.insulin) {
            dataLabel.setText(R.string.insulin);
            addData.setText(R.string.add_insulin);

            currentData = DataType.insulin;
        }
    }

    public void IncrementDecrementDate(boolean increment) {

        if (increment) {
            chosenDate = chosenDate.plusDays(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy", Locale.ENGLISH);
            chosenDateText.setText(formatter.format(chosenDate));
        }
        else {
            chosenDate = chosenDate.minusDays(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy", Locale.ENGLISH);
            chosenDateText.setText(formatter.format(chosenDate));
        }

        Log.d("activity", "Date: " + chosenDate);
        Log.d("activity", "Today: " + LocalDate.now());
        if (chosenDate.isEqual(LocalDate.now())) {
            incrementDate.setEnabled(false);
        }
        else {
            incrementDate.setEnabled(true);
        }
        UpdateList(currentData);
    }


    public void UpdateList(DataType dataType) {
        if (dataType == DataType.bloodSugar) {
            final BloodSugarListAdapter bloodSugarAdapter = new BloodSugarListAdapter(new BloodSugarListAdapter.DataDiff());
            recyclerView.setAdapter(bloodSugarAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Update the cached copy of the words in the adapter.
            dataViewModel.getBloodSugarByDates(chosenDate.atTime(0,0),
                    chosenDate.plusDays(1).atTime(0,0)).observe(this, bloodSugarAdapter::submitList);
        }
        else if (dataType == DataType.carb) {
            final CarbListAdapter carbAdapter = new CarbListAdapter(new CarbListAdapter.DataDiff());
            recyclerView.setAdapter(carbAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Update the cached copy of the words in the adapter.
            dataViewModel.getCarbByDates(chosenDate.atTime(0,0),
                    chosenDate.plusDays(1).atTime(0,0)).observe(this, carbAdapter::submitList);
        }
        else if (dataType == DataType.insulin) {
            final InsulinListAdapter insulinAdapter = new InsulinListAdapter(new InsulinListAdapter.DataDiff());
            recyclerView.setAdapter(insulinAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Update the cached copy of the words in the adapter.
            dataViewModel.getInsulinByDates(chosenDate.atTime(0,0),
                    chosenDate.plusDays(1).atTime(0,0)).observe(this, insulinAdapter::submitList);
        }
    }
}