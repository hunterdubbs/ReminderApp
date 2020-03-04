package net.cit368.reminderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class EditTaskActivity extends AppCompatActivity {

    private DatePickerDialog datePicker;
    private AppCompatImageButton dateBtn, locBtn;
    private TextView locationText, dateText;


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

        dateBtn = (AppCompatImageButton) findViewById(R.id.editDateBtn);
        locBtn = (AppCompatImageButton) findViewById(R.id.editLocBtn);
        dateText = (TextView) findViewById(R.id.txtTaskDate);
        locationText = (TextView) findViewById(R.id.txtTaskLocation);

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

            }
        });
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
                        //Set text = dd/mm/yyyy
                        dateText.setText((inMonth + 1) + "/" + inDay + "/" + inYear);
                    }
                },
                year, month, day);
        datePicker.show();
    }
}
