package maximedelange.tasklistemail.Domain;

import java.util.Date;

/**
 * Created by M on 8/17/2017.
 */

public class Task {
    // Fields
    private Integer taskID = 0;
    private String subject = null;
    private String message = null;
    private Date dateCreated = null;
    private Date endDate = null;

    // Constructor
    public Task(String subject, String message, Date dateCreated, Date endDate){
        this.subject = subject;
        this.message = message;
        this.dateCreated = dateCreated;
        this.endDate = endDate;
    }

    public Task(){
        // Initializing purpose only.
    }

    // Methods
    public void setTaskID(int taskID){
        this.taskID = taskID;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setDateCreated(Date dateCreated){
        this.dateCreated = dateCreated;
    }

    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }

    public Integer getTaskID(){
        return this.taskID;
    }

    public String getSubject(){
        return this.subject;
    }

    public String getMessage(){
        return this.message;
    }

    public Date getDateCreated(){
        return this.dateCreated;
    }

    public Date getEndDate(){
        return this.endDate;
    }

    @Override
    public String toString(){
        String info = "Subject: " + getSubject();
        return info;
    }
}
