package sample.Models;

public class Student {
    private String fio;
    private String group;
    private String task;
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

    public String getMasterComment() {
        return masterComment;
    }

    public void setTask(String task) {
        Student.this.task = task;
    }

    public void setMasterComment(String masterComment) {
        Student.this.masterComment = masterComment;
    }
}
