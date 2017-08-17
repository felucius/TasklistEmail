package maximedelange.tasklistemail.Screen;

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
import android.widget.EditText;

import java.util.Date;

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
    private Mail mail = null;
    private Database database = null;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Additions
        removeActionBar();
        mail = new Mail();
        addTaskToEmail();
        addTaskToDatabase();
        goToTasks();
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

    private void addTaskToDatabase(){
        addTask = (Button) findViewById(R.id.btnAddTaskDatabase);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subject = (EditText) findViewById(R.id.txtSubject);
                message = (EditText) findViewById(R.id.txtMessage);
                if(!subject.getText().toString().equals("") && !message.getText().toString().equals("")){
                    Task task = new Task(subject.getText().toString(), message.getText().toString(), new Date());
                    database = new Database(context);
                    database.addTask(task);
                }else{
                    System.out.println("Fill in all the fields.");
                }
            }
        });
    }

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
}
