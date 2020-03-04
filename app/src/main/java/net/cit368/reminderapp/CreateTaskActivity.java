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

import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity {

    private TextView dateText;
    String location;
    private Button submitBtn;
    private AppCompatImageButton dateBtn, locBtn;
    private DatePickerDialog datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        dateBtn = (AppCompatImageButton) findViewById(R.id.dateBtn);
        locBtn = (AppCompatImageButton) findViewById(R.id.locBtn);
        submitBtn = (Button) findViewById(R.id.submitTask);

        dateText = (TextView) findViewById(R.id.txtTaskDate);

        //Prompt date picker
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateBox();
            }
        });

        //TODO
        //Prompt location picker
        locBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateTaskActivity.this, MapsActivityCurrentPlace.class);
                startActivityForResult(intent, 2);// Activity is started with requestCode 2
            }
        });

        //TODO
        //Submit information to database and return to TaskActivity view
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check if the request code is same as what is passed
        TextView locationText = (TextView) findViewById(R.id.txtTaskLocation);

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            locationText.setText(data.getStringExtra("ADDRESS"));
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
                        dateText.setText((inMonth + 1) + "/" + inDay + "/" + inYear);
                    }
                },
                year, month, day);
        datePicker.show();
    }
}
