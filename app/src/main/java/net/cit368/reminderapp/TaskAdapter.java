package net.cit368.reminderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.cit368.reminderapp.R;

/**
 * @author Tyler Kroposki
 * RecyclerView adapter to add the File information for each file in a User's SFTP directory.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    private TaskItem[] taskList;
    private LayoutInflater inflator;

    TaskAdapter(Context context, TaskItem[] data) {
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

        String taskName = taskList[position].getTaskName();
        String taskDate = String.valueOf(taskList[position].getTaskDate());
        String taskDuration = String.valueOf(taskList[position].getTaskDuration());
        String taskLocation = String.valueOf(taskList[position].getTaskLocation());

        holder.taskName.setText(taskName);
        holder.taskDate.setText("Date: " + taskDate);
        holder.taskDuration.setText("Last Duration: " + taskDuration);
        holder.taskLocation.setText("Location: " + taskLocation);

    }

    @Override
    public int getItemCount() {
        return taskList.length;
    }

    //Stores the views
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        TextView taskDate;
        TextView taskDuration;
        TextView taskLocation;

        ViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.recycleTaskName);
            taskDate = itemView.findViewById(R.id.recycleTaskDate);
            taskDuration = itemView.findViewById(R.id.recycleTaskDuration);
            taskLocation = itemView.findViewById(R.id.recycleTaskLocation);
        }

    }
}
