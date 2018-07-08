package sample;

public class Student {
    private String fio;
    private String group;
    private String task;
    private Boolean taskDone;
    private String taskDate;
    private String masterComment;

    public Student(String fio, String group) {
        this.fio = fio;
        this.group = group;
    }

    public String getFio() {
        return fio;
    }

    public String getGroup() {
        return group;
    }

    public String getTask() {
        return task;
    }

    public Boolean getTaskDone() {
        return taskDone;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public String getMasterComment() {
        return masterComment;
    }

    public void setTask(String task) {
        Student.this.task = task;
    }

    public void setTaskDone(Boolean taskDone) {
        Student.this.taskDone = taskDone;
    }

    public void setTaskDate(String taskDate) {
        Student.this.taskDate = taskDate;
    }

    public void setMasterComment(String masterComment) {
        Student.this.masterComment = masterComment;
    }
}
