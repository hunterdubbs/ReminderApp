package net.cit368.reminderapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * @author Tyler Kroposki, Hunter Dubbs
 * @version 2/29/2020
 * This is the core activity of the app. It displays the existing tasks of the user and allows
 * for new tasks to be created.
 */
public class TaskActivity extends AppCompatActivity {

    /**
     * Setup form buttons and handlers
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        final Button logoutBtn = findViewById(R.id.logoutBtn);
        final Button taskBtn = findViewById(R.id.newTaskBtn);
        final Button accountBtn = findViewById(R.id.accountBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance().signOut(getApplicationContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                System.out.println("======================LOG OUT SUCCESSFUL======================");
                                startActivity(new Intent(TaskActivity.this, MainActivity.class));
                            }
                        });
            }
        });
        taskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TaskActivity.this, CreateTaskActivity.class));
            }
        });
        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaskActivity.this, AccountActivity.class));
            }
        });
    }


}
