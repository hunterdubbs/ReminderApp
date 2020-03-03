package net.cit368.reminderapp;

public class TaskItem {

    private String taskName;
    private String taskDate;
    private int taskDuration;
    private String taskLocation;

    public TaskItem(String taskName, String taskDate, int taskDuration, String taskLocation) {
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.taskDuration = taskDuration;
        this.taskLocation = taskLocation;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public int getTaskDuration() {
        return taskDuration;
    }

    public void setTaskDuration(int taskDuration) {
        this.taskDuration = taskDuration;
    }

    public String getTaskLocation() {
        return taskLocation;
    }

    public void setTaskLocation(String taskLocation) {
        this.taskLocation = taskLocation;
    }
}
