package net.cit368.reminderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * @author Hunter Dubbs
 * @version 3/5/2020
 */
public class EditTaskActivity extends AppCompatActivity {

    private DatePickerDialog datePicker;
    private AppCompatImageButton dateBtn, locBtn;
    private TextView locationText, dateText, nameText;
    private String name, date, location, taskId;
    private boolean complete;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    /**
     *
     *  TaskActivity -> (Task Intent)
     *                  EditTaskActivity
     *                      -> Modify Text
     *                      -> Submit
     *                      -> Return to MainActivity & Refresh RecyclerView
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        dateBtn = findViewById(R.id.editDateBtn);
        locBtn = findViewById(R.id.editLocBtn);
        nameText = findViewById(R.id.txtEditTaskName);
        dateText = findViewById(R.id.txtEditTaskDate);
        locationText = findViewById(R.id.txtEditTaskLocation);

        Bundle data = getIntent().getExtras();
        taskId = data.getString("taskId");
        name = data.getString("taskName");
        date = data.getString("taskDate");
        location = data.getString("taskLocation");
        complete = data.getBoolean("taskComplete");

        nameText.setText(name);
        dateText.setText(date);
        locationText.setText(location);

        Button btnSubmit = findViewById(R.id.submitEditTask);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameText.getText().toString();
                if(validate()){
                    TaskItem updatedTask = new TaskItem(name, date, complete, location, taskId);
                    database.child("users").child(userUid).child("tasks").child(taskId).setValue(updatedTask);
                    finish();
                }
            }
        });

        //Prompt date picker
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateBox();
            }
        });

        //Prompt location picker
        locBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTaskActivity.this, MapsActivityCurrentPlace.class);
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
        datePicker = new DatePickerDialog(EditTaskActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int inYear, int inMonth, int inDay) {
                        date = (inMonth + 1) + "/" + inDay + "/" + inYear;
                        dateText.setText(date);
                    }
                },
                year, month, day);
        datePicker.show();
    }

    private boolean validate(){
        return validateTaskName() && validateTaskDate() && validateTaskLocation();
    }

    private boolean validateTaskName(){
        if(name != null && !name.isEmpty()){
            return true;
        }
        Toast.makeText(EditTaskActivity.this, "Enter task name", Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean validateTaskDate(){
        if(date != null && !date.isEmpty()){
            return true;
        }
        Toast.makeText(EditTaskActivity.this, "Enter task date", Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean validateTaskLocation(){
        if(location != null && !location.isEmpty()){
            return true;
        }
        Toast.makeText(EditTaskActivity.this, "Enter task location", Toast.LENGTH_SHORT).show();
        return false;
    }
}
