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
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private DataViewModel dataViewModel;
    private Date chosenDate;
    private DataType currentData;
    private Button insulinButton;
    private Button bloodSugarButton;
    private Button carbButton;
    private Button addData;
    private TextView dataLabel;
    private TextView timeLabel;
    private TextView time;
    private EditText dataInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chosenDate = Calendar.getInstance().getTime();
        currentData = DataType.bloodSugar;

        insulinButton = findViewById(R.id.chooseInsulin);
        bloodSugarButton = findViewById(R.id.chooseBS);
        carbButton = findViewById(R.id.chooseCarb);


        addData = findViewById(R.id.addData);

        dataLabel = findViewById(R.id.dataLabel);
        timeLabel = findViewById(R.id.timeLabel);
        time = findViewById(R.id.time);
        dataInput = findViewById(R.id.dataInput);




        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final BloodSugarListAdapter adapter = new BloodSugarListAdapter(new BloodSugarListAdapter.DataDiff());
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

        //TextView time = findViewById(R.id.time);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        time.setText(timeFormat.format(Calendar.getInstance().getTime()));


        insulinButton.setOnClickListener(v -> {
            dataLabel.setText("Insulin: ");
            addData.setText("Add Insulin");

            final InsulinListAdapter insulinAdapter = new InsulinListAdapter(new InsulinListAdapter.DataDiff());
            recyclerView.setAdapter(insulinAdapter);

            dataViewModel.getInsulin().observe(this, data -> {
                // Update the cached copy of the words in the adapter.
                insulinAdapter.submitList(data);
            });

            currentData = DataType.insulin;
        });
        bloodSugarButton.setOnClickListener(v -> {
            dataLabel.setText(R.string.blood_sugar);
            addData.setText(R.string.add_blood_sugar);

            recyclerView.setAdapter(adapter);
            dataViewModel.getBloodSugar().observe(this, data -> {
                // Update the cached copy of the words in the adapter.
                adapter.submitList(data);
            });
            currentData = DataType.bloodSugar;
        });
        carbButton.setOnClickListener(v -> {
            dataLabel.setText("Carbs: ");
            addData.setText("Add Carbs");

            final CarbListAdapter carbAdapter = new CarbListAdapter(new CarbListAdapter.DataDiff());
            recyclerView.setAdapter(carbAdapter);

            dataViewModel.getCarbs().observe(this, data -> {
                // Update the cached copy of the words in the adapter.
                carbAdapter.submitList(data);
            });
            currentData = DataType.carb;
        });
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

        //Button addBloodSugar = findViewById(R.id.addData);
        addData.setOnClickListener(v -> {

            //EditText bsInput = findViewById(R.id.dataInput);
            //TextView bsTime = findViewById(R.id.time);
            int input = Integer.parseInt(dataInput.getText().toString());
            Date bsDate = chosenDate;

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

            bsDate.setHours(hours);
            bsDate.setMinutes(minutes);

            if (currentData == DataType.bloodSugar) {
                BloodSugar bloodSugar = new BloodSugar(input, bsDate);

                dataViewModel.insertBloodSugar(bloodSugar);
            }
            else if (currentData == DataType.carb) {
                Carb carb = new Carb(input, bsDate);

                dataViewModel.insertCarb(carb);
            }
            else if (currentData == DataType.insulin) {
                Insulin insulin = new Insulin(input, bsDate);

                dataViewModel.insertInsulin(insulin);
            }

        });
    }


}