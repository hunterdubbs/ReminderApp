package net.cit368.reminderapp;

/**
 * @author Tyler Kroposki, Hunter Dubbs
 * @version 3/3/2020
 * TaskItem is an object used to store the individual tasks during program execution.
 */
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

    /**
     * Default task item constructor used as in-between step for snapshot syncs
     */
    public TaskItem(){
        this.taskName = "";
        this.taskDate = "";
        this.isComplete = false;
        this.taskLocation = "";
        this.taskId = "";
    }

    /**
     * Gets the task item id
     * @return taskId
     */
    public String getTaskId(){
        return taskId;
    }

    /**
     * Sets the task item id
     * @param taskId the new taskId
     */
    public void setTaskId(String taskId){
        this.taskId = taskId;
    }

    /**
     * Gets the task name
     * @return taskName
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Sets the task name
     * @param taskName the new taskName
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Gets the task date
     * @return taskDate
     */
    public String getTaskDate() {
        return taskDate;
    }

    /**
     * Sets the task date
     * @param taskDate the new taskDate
     */
    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    /**
     * Gets the completion status
     * @return completion status
     */
    public boolean getComplete() {
        return isComplete;
    }

    /**
     * Sets the completion status
     * @param isComplete the new completion status
     */
    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    /**
     * Gets the task location
     * @return taskLocation
     */
    public String getTaskLocation() {
        return taskLocation;
    }

    /**
     * Sets the task location
     * @param taskLocation the new task location
     */
    public void setTaskLocation(String taskLocation) {
        this.taskLocation = taskLocation;
    }
}
