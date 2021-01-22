package batson.diabetestrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

    private Button addData;
    private Button incrementDate;
    private Button decrementDate;

    private Spinner dropDown;

    private TextView dataLabel;
    private TextView timeLabel;
    private TextView time;
    private TextView chosenDateText;
    private EditText dataInput;
    private RecyclerView recyclerView;

    private View popup;
    private String typeToDelete;
    private int idToDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up data select drop down menu
        dropDown = findViewById(R.id.dropDownSelect);
        String[] dataTypeList = new String[] {"Blood Sugar", "Insulin", "Carb"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, dataTypeList);
        dropDown.setAdapter(adapter);

        // Buttons
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

        // Set the chosen date to be the devices current date
        chosenDateText = findViewById(R.id.chosenDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy", Locale.ENGLISH);
        chosenDateText.setText(formatter.format(LocalDate.now()));


        // Set time to current time
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
        time.setText(timeFormat.format(LocalDateTime.now()));

        dropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = dropDown.getSelectedItem().toString();

                SwitchInput(stringToDataType(selectedItemText));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dropDown.setSelection(0);

                String selectedItemText = dropDown.getSelectedItem().toString();

                SwitchInput(stringToDataType(selectedItemText));
            }
        });

        incrementDate.setOnClickListener(v -> IncrementDecrementDate(true));
        decrementDate.setOnClickListener(v -> IncrementDecrementDate(false));

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

        addData.setOnClickListener(v -> {

            int input;
            try {
                input = Integer.parseInt(dataInput.getText().toString());
            } catch (NumberFormatException e) {
                return;
            }

            String amPm = time.getText().toString().split(":")[1].split(" ")[1];

            int hours = Integer.parseInt(time.getText().toString().split(":")[0]);
            int minutes = Integer.parseInt(time.getText().toString().split(":")[1].split(" ")[0]);

            if (amPm.equals("PM")) {
                hours += 12;
            }
            else if (hours == 12) {
                hours = 0;
            }

            if (hours == 24) {
                hours = 12;
            }

            LocalDateTime localDateTime = chosenDate.atTime(hours, minutes);

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

    public DataType stringToDataType(String dataString) {

        DataType dataType = DataType.bloodSugar;

        switch (dataString) {
            case "Blood Sugar":
                dataType = DataType.bloodSugar;
                break;
            case "Carb":
                dataType = DataType.carb;
                break;
            case "Insulin":
                dataType = DataType.insulin;
                break;
        }

        return dataType;
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

        // We don't want to increment past today's date
        incrementDate.setEnabled(!chosenDate.isEqual(LocalDate.now()));

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

    public void deletePopup(View view) {
        TextView realView = (TextView) view;
        idToDelete = Integer.parseInt(realView.getHint().toString().split(" ")[0]);
        typeToDelete = realView.getHint().toString().split(" ")[1];

        LayoutInflater inflater = getLayoutInflater();
        popup = inflater.inflate(R.layout.delete_popup, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popup, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        TextView text = findViewById(R.id.deletePopupText);
        Button yes = findViewById(R.id.yesButton);
        Button no = findViewById(R.id.noButton);

    }

    public void confirmDelete(View view) {
        switch (typeToDelete) {
            case "B":
                AsyncTask.execute(() -> dataViewModel.deleteBloodSugarById(idToDelete));
            case "I":
                AsyncTask.execute(() -> dataViewModel.deleteInsulinById(idToDelete));
            case "C":
                AsyncTask.execute(() -> dataViewModel.deleteCarbById(idToDelete));
        }
        ((ViewGroup) popup.getParent()).removeView(popup);
    }

    public void closeDeletePopup(View v) {
        ((ViewGroup) popup.getParent()).removeView(popup);
    }

}