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

public class CreateTaskActivity extends AppCompatActivity {

    private TextView dateText, txtTaskName;
    private String location, taskName, date;
    private Button submitBtn;
    private AppCompatImageButton dateBtn, locBtn;
    private DatePickerDialog datePicker;

    private DatabaseReference database;
    private String userUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        database = FirebaseDatabase.getInstance().getReference();
        userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        dateBtn = (AppCompatImageButton) findViewById(R.id.dateBtn);
        locBtn = (AppCompatImageButton) findViewById(R.id.locBtn);
        submitBtn = (Button) findViewById(R.id.submitTask);
        dateText = (TextView) findViewById(R.id.txtTaskDate);
        txtTaskName = findViewById(R.id.txtTaskName);

        //Prompt date picker
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateBox();
            }
        });

        locBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateTaskActivity.this, MapsActivityCurrentPlace.class);
                startActivityForResult(intent, 2);// Activity is started with requestCode 2
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

    private boolean validate(){
        return validateTaskName() && validateTaskDate() && validateTaskLocation();
    }

    private boolean validateTaskName(){
        if(taskName != null && !taskName.isEmpty()){
            return true;
        }
        Toast.makeText(CreateTaskActivity.this, "Enter task name", Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean validateTaskDate(){
        if(date != null && !date.isEmpty()){
            return true;
        }
        Toast.makeText(CreateTaskActivity.this, "Enter task date", Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean validateTaskLocation(){
        if(location != null && !location.isEmpty()){
            return true;
        }
        Toast.makeText(CreateTaskActivity.this, "Enter task location", Toast.LENGTH_SHORT).show();
        return false;
    }

    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check if the request code is same as what is passed
        TextView locationText = (TextView) findViewById(R.id.txtTaskLocation);

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

        //Date picker dialogue box
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
