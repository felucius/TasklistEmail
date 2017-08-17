package maximedelange.tasklistemail.Domain;

import android.content.Intent;

import java.util.Date;

/**
 * Created by M on 8/17/2017.
 */

public class Mail {
    // Fields
    private Intent sendMail = null;
    private Date dateToday = null;

    // Constructor
    public Mail(){

    }

    public Intent sendMail(String messageType, String[] addressedTo, String subject, String message){
        dateToday = new Date();
        sendMail = new Intent(Intent.ACTION_SEND);
        sendMail.setType(messageType);
        sendMail.putExtra(Intent.EXTRA_EMAIL, addressedTo);
        sendMail.putExtra(Intent.EXTRA_SUBJECT, subject);
        sendMail.putExtra(Intent.EXTRA_TEXT, dateToday + "\n\n" +  message);

        return sendMail;
    }
}
