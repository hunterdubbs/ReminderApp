package net.cit368.reminderapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * @author Tyler Kroposki, Hunter Dubbs
 * @version 3/4/2020
 * This is the core activity of the app. It displays the existing tasks of the user and allows
 * for new tasks to be created.
 */
public class TaskActivity extends AppCompatActivity {

    private DatabaseReference database;
    private String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    //will hold returned tasks from database
    private ArrayList<TaskItem> taskList;
    private ArrayList<String> taskIdList;

    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;

    /**
     * Setup form buttons and data handlers
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        final Button logoutBtn = findViewById(R.id.logoutBtn);
        final Button taskBtn = findViewById(R.id.newTaskBtn);
        final Button accountBtn = findViewById(R.id.accountBtn);
        //logout
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance().signOut(getApplicationContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.i("Auth", "Logged out");
                                startActivity(new Intent(TaskActivity.this, MainActivity.class));
                            }
                        });
            }
        });
        //add task
        taskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TaskActivity.this, CreateTaskActivity.class));
            }
        });
        //change account password
        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaskActivity.this, AccountActivity.class));
            }
        });

        //stores tasks for signed in user
        database = FirebaseDatabase.getInstance().getReference().child("users").child(userUid).child("tasks");
        taskList = new ArrayList<>();
        taskIdList = new ArrayList<>();

        //update with newly added items and get initial list
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskList.removeAll(taskList);
                taskList.removeAll(taskIdList);
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    taskList.add(snapshot.getValue(TaskItem.class));
                    taskIdList.add(snapshot.getKey());
                }
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Database Error", "Could not retrieve database entries", databaseError.toException());
            }
        });

        //recyclerview setup
        recyclerView = findViewById(R.id.taskRecyclerView);
        taskAdapter = new TaskAdapter(this, taskList);

        //swipe right to complete or reset completion of task and left to remove task
        ItemTouchHelper completeTaskHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int targetPos = viewHolder.getAdapterPosition();
                if(direction == ItemTouchHelper.RIGHT){ //toggle complete
                    if(taskList.get(targetPos).getComplete()) {
                        database.child(taskIdList.get(targetPos)).child("complete").setValue(false).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                taskAdapter.notifyDataSetChanged();
                            }
                        });
                        taskList.get(targetPos).setComplete(false);
                    }else{
                        database.child(taskIdList.get(targetPos)).child("complete").setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                taskAdapter.notifyDataSetChanged();
                            }
                        });
                        taskList.get(targetPos).setComplete(true);
                    }
                    taskAdapter.notifyItemChanged(targetPos);
                }else if(direction == ItemTouchHelper.LEFT){ //remove task
                    database.child(taskIdList.get(targetPos)).removeValue();
                    taskList.remove(targetPos);
                    taskIdList.remove(targetPos);
                    taskAdapter.notifyItemRemoved(targetPos);
                }
            }
        });
        completeTaskHelper.attachToRecyclerView(recyclerView);

        //bind adapter to RecyclerView
        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
