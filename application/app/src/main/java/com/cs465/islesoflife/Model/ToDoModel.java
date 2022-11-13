package com.cs465.islesoflife.Model;

public class ToDoModel {
    private int id, status;
    private String task;
    private String category;
    private String taskCreatedDate;
    private String taskDueDate;
    private String taskDueTime;
    private String taskImportance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTaskCreatedDate() {
        return taskCreatedDate;
    }

    public void setTaskCreatedDate(String date) {
        this.taskCreatedDate = date;
    }

    public String getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(String date) {
        this.taskDueDate = date;
    }

    public String getTaskDueTime() {
        return taskDueTime;
    }

    public void setTaskDueTime(String time) {
        this.taskDueTime = time;
    }

    public String getImportance() {
        return taskImportance;
    }

    public void setImportance(String importance) {
        this.taskImportance = importance;
    }
}
