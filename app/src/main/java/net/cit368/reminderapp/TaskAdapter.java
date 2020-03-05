package net.cit368.reminderapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.cit368.reminderapp.R;

import java.util.ArrayList;

/**
 * @author Tyler Kroposki, Hunter Dubbs
 * @version 3/5/2020
 * RecyclerView adapter to bind data to the item views.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    private ArrayList<TaskItem> taskList;
    private LayoutInflater inflator;
    private Context context;

    /**
     * Constructor for the adapter that includes the TaskActivity context and the data to display
     * @param context the TaskActivity context
     * @param data and ArrayList of TaskItem objects to be displayed
     */
    TaskAdapter(Context context, ArrayList<TaskItem> data) {
        this.inflator = LayoutInflater.from(context);
        this.taskList = data;
        this.context = context;
    }

    /**
     * Inflates each object from the xml specification
     * @param parent the parent of the ViewHolder
     * @param viewType the type of view
     * @return the ViewHolder with the inflated items
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.fileitem, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds the tasks to items in the ViewHolder
     * @param holder the ViewHolder containing the items
     * @param position the position of the item being bound
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final String taskName = taskList.get(position).getTaskName();
        String taskDate = String.valueOf(taskList.get(position).getTaskDate());
        String taskLocation = String.valueOf(taskList.get(position).getTaskLocation());

        holder.taskName.setText(taskName);
        holder.taskDate.setText("Date: " + taskDate);
        holder.taskLocation.setText("Location: " + taskLocation);
        //color items based upon their completion status
        if(taskList.get(position).getComplete()){
            holder.itemView.setBackgroundColor(Color.GREEN);
        }else{
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        //click items to edit them
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass information about the task to the EditTaskActivity so that it can be modified
                context.startActivity(new Intent(context, EditTaskActivity.class)
                        .putExtra("taskId", taskList.get(position).getTaskId())
                        .putExtra("taskName", taskList.get(position).getTaskName())
                        .putExtra("taskDate", taskList.get(position).getTaskDate())
                        .putExtra("taskLocation", taskList.get(position).getTaskLocation())
                        .putExtra("taskComplete", taskList.get(position).getComplete()));
            }
        });

    }

    /**
     * Gets a count of the number of tasks
     * @return the number of tasks
     */
    @Override
    public int getItemCount() {
        return taskList.size();
    }

    /**
     * The base ViewHolder that displays the task name, date, and location
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        TextView taskDate;
        TextView taskLocation;

        ViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.recycleTaskName);
            taskDate = itemView.findViewById(R.id.recycleTaskDate);
            taskLocation = itemView.findViewById(R.id.recycleTaskLocation);
        }

    }
}
