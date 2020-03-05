package net.cit368.reminderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * @author Hunter Dubbs, TYler Kroposki
 * @version 3/4/2020
 * This activity allows the user to create a new task to be added to their list.
 */
public class CreateTaskActivity extends AppCompatActivity {

    private TextView dateText, txtTaskName;
    private String location, taskName, date;
    private Button submitBtn;
    private AppCompatImageButton dateBtn, locBtn;
    private DatePickerDialog datePicker;

    private DatabaseReference database;
    private String userUid;

    /**
     * Create and bind input fields and button click handlers
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        //get firebase references
        database = FirebaseDatabase.getInstance().getReference();
        userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        dateBtn = findViewById(R.id.dateBtn);
        locBtn = findViewById(R.id.locBtn);
        submitBtn = findViewById(R.id.submitTask);
        dateText = findViewById(R.id.txtTaskDate);
        txtTaskName = findViewById(R.id.txtTaskName);

        //date picker
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateBox();
            }
        });

        //location picker
        locBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateTaskActivity.this, MapsActivityCurrentPlace.class);
                startActivityForResult(intent, 2);
            }
        });

        //Submit information to database and return to TaskActivity view
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskName = txtTaskName.getText().toString();
                if(validate()){
                    String taskKey = database.child("users").child(userUid).child("tasks").push().getKey();
                    TaskItem taskItem = new TaskItem(taskName, date, false, location, taskKey);
                    database.child("users").child(userUid).child("tasks").child(taskKey).setValue(taskItem);
                    finish();
                }
            }
        });
    }

    /**
     * Validates all fields in the activity
     * @return true if fields validate successfully
     */
    private boolean validate(){
        return validateTaskName() && validateTaskDate() && validateTaskLocation();
    }

    /**
     * Validates the task name to ensure it exists
     * @return true if task name validates successfully
     */
    private boolean validateTaskName(){
        if(taskName != null && !taskName.isEmpty()){
            return true;
        }
        Toast.makeText(CreateTaskActivity.this, "Enter task name", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * Validates the task date to ensure it is set
     * @return true if task date validates successfully
     */
    private boolean validateTaskDate(){
        if(date != null && !date.isEmpty()){
            return true;
        }
        Toast.makeText(CreateTaskActivity.this, "Enter task date", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * Validates the task location to ensure one is set
     * @return true if task location validates successfully
     */
    private boolean validateTaskLocation(){
        if(location != null && !location.isEmpty()){
            return true;
        }
        Toast.makeText(CreateTaskActivity.this, "Enter task location", Toast.LENGTH_SHORT).show();
        return false;
    }


    /**
     * Callback method to handle returned data from location picker activity
     * @param requestCode request code of location picker
     * @param resultCode result code of location picker
     * @param data data returned from location picker
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TextView locationText = findViewById(R.id.txtTaskLocation);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            location = data.getStringExtra("ADDRESS");
            locationText.setText(location);
        }
    }

    /**
     * Open a dialogue box that allows a user to select a date, and then
     * assign the text from the prompt to a TextView.
     */
    private void openDateBox() {
        final Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);

        datePicker = new DatePickerDialog(CreateTaskActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int inYear, int inMonth, int inDay) {
                        //Set text = dd/mm/yyyy
                        date = (inMonth + 1) + "/" + inDay + "/" + inYear;
                        dateText.setText(date);
                    }
                },
                year, month, day);
        datePicker.show();
    }
}
