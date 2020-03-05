package net.cit368.reminderapp;

public class TaskItem {

    private String taskName;
    private String taskDate;
    private boolean isComplete;
    private String taskLocation;
    private String taskId;

    /**
     * Creates a task item with all fields
     * @param taskName name
     * @param taskDate date
     * @param isComplete completion status
     * @param taskLocation location
     * @param taskId unique task id
     */
    public TaskItem(String taskName, String taskDate, boolean isComplete, String taskLocation, String taskId) {
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.isComplete = isComplete;
        this.taskLocation = taskLocation;
        this.taskId = taskId;
    }

    public TaskItem(){
        this.taskName = "";
        this.taskDate = "";
        this.isComplete = false;
        this.taskLocation = "";
        this.taskId = "";
    }

    public String getTaskId(){
        return taskId;
    }

    public void setTaskId(String taskId){
        this.taskId = taskId;
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

    public boolean getComplete() {
        return isComplete;
    }

    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public String getTaskLocation() {
        return taskLocation;
    }

    public void setTaskLocation(String taskLocation) {
        this.taskLocation = taskLocation;
    }
}
