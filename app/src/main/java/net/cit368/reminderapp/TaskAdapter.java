package net.cit368.reminderapp;

import android.content.Context;
import android.graphics.Color;
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
 * @author Tyler Kroposki
 * RecyclerView adapter to add the File information for each file in a User's SFTP directory.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    private ArrayList<TaskItem> taskList;
    private LayoutInflater inflator;

    TaskAdapter(Context context, ArrayList<TaskItem> data) {
        this.inflator = LayoutInflater.from(context);
        this.taskList = data;
    }

    //Inflate row from fileitem xml
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.fileitem, parent, false);
        return new ViewHolder(view);
    }

    //Put the text into the TextView
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String taskName = taskList.get(position).getTaskName();
        String taskDate = String.valueOf(taskList.get(position).getTaskDate());
        String taskLocation = String.valueOf(taskList.get(position).getTaskLocation());

        holder.taskName.setText(taskName);
        holder.taskDate.setText("Date: " + taskDate);
        holder.taskLocation.setText("Location: " + taskLocation);
        if(taskList.get(position).getComplete()){
            holder.itemView.setBackgroundColor(Color.GREEN);
        }else{
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    //Stores the views
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
