package maximedelange.tasklistemail.Domain;

import android.content.Intent;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by M on 8/22/2017.
 */

public class CalendarEvent {
    // Fields
    private Intent sendCalendarEvent = null;

    // Constructor
    public CalendarEvent(){

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Intent sendCalendarEvent(String title, String eventLocation, String description, Date calendarDate){
        sendCalendarEvent = new Intent(Intent.ACTION_INSERT);
        sendCalendarEvent.setData(CalendarContract.Events.CONTENT_URI);

        // Adding values to the calendar event.
        sendCalendarEvent.setType("vnd.android.cursor.item/event");
        sendCalendarEvent.putExtra(CalendarContract.Events.TITLE, title);
        sendCalendarEvent.putExtra(CalendarContract.Events.EVENT_LOCATION, eventLocation);
        sendCalendarEvent.putExtra(CalendarContract.Events.DESCRIPTION, description);

        // Adding dates and times tro the calendar event.
        sendCalendarEvent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        sendCalendarEvent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendarDate.getTime());
        sendCalendarEvent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calendarDate.getTime());

        return sendCalendarEvent;
    }

}
