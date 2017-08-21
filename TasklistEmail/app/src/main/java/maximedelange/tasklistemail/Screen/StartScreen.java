package maximedelange.tasklistemail.Screen;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import maximedelange.tasklistemail.Domain.Database;
import maximedelange.tasklistemail.Domain.Mail;
import maximedelange.tasklistemail.Domain.Task;
import maximedelange.tasklistemail.R;

public class StartScreen extends AppCompatActivity {

    // Fields
    private Button addTask = null;
    private Button goTo = null;
    private EditText subject = null;
    private EditText message = null;
    private EditText addDate = null;
    private Mail mail = null;
    private Database database = null;
    private Context context = this;
    private Date taskEndDate = null;
    private Calendar calendar = null;

    private SimpleDateFormat simpleDateFormat = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Additions
        removeActionBar();
        mail = new Mail();
        calendar = Calendar.getInstance();
        addTaskToEmail();
        addTaskToDatabase();
        goToTasks();
        addDate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    Adding a task to the SQLite database.
     */
    private void addTaskToDatabase(){
        addTask = (Button) findViewById(R.id.btnAddTaskDatabase);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task taskHolder = new Task();
                subject = (EditText) findViewById(R.id.txtSubject);
                message = (EditText) findViewById(R.id.txtMessage);
                if(!subject.getText().toString().equals("") && !message.getText().toString().equals("") && !addDate.getText().toString().equals("")){
                    // Parsing the date from the input text to an actual date object.
                    try {
                        taskEndDate = simpleDateFormat.parse(addDate.getText().toString());
                    }catch (ParseException parseEx){
                        parseEx.printStackTrace();
                    }
                    // Add a task with the values from the text fields.
                    Task task = new Task(subject.getText().toString(), message.getText().toString(), new Date(), taskEndDate);
                    database = new Database(context);
                    // Adding the task to the database.
                    database.addTask(task);
                }else{
                    System.out.println("Fill in all the fields.");
                }
            }
        });
    }

    /*
    Adding a task to email with an intent.
     */
    private void addTaskToEmail(){
        addTask = (Button) findViewById(R.id.btnAddTaskEmail);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                subject = (EditText) findViewById(R.id.txtSubject);
                message = (EditText) findViewById(R.id.txtMessage);

                // Array is needed to paste the email address into the mail recipient.
                String[] addressedTo = {"maximedelange1991@hotmail.com"};

                if(!subject.getText().toString().equals("") && !message.getText().toString().equals("")){
                    Intent sendEmail = mail.sendMail("message/rfc822", addressedTo,
                            subject.getText().toString(), message.getText().toString());
                    try {
                        startActivity(Intent.createChooser(sendEmail, "Send email..."));
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }else{
                    System.out.println("Fill in all the fields.");
                }
            }
        });
    }

    /*
    Navigate to the task list on a button click event.
     */
    private void goToTasks(){
        goTo = (Button) findViewById(R.id.btnShowTasks);
        goTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent taskPage = new Intent(view.getContext(), TaskScreen.class);
                startActivity(taskPage);
            }
        });
    }

    private void removeActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    /*
    Adding a taskEndDate to a task.
     */
    private void addDate(){
        addDate = (EditText) findViewById(R.id.txtAddDate);
        addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context, datePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                setDateText();
            }
        });
    }

    public void setDateText(){
        simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
        addDate.setText(simpleDateFormat.format(calendar.getTime()));
    }

    DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);

        }
    };

}
