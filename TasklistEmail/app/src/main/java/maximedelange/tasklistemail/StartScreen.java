package maximedelange.tasklistemail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class StartScreen extends AppCompatActivity {

    // Fields
    private Button addTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Additions
        addTask();
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

    private void addTask(){
        addTask = (Button) findViewById(R.id.btnAddTask);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] TO = {"maximedelange1991@hotmail.com"};
                String[] CC = {""};

                Intent sendEmail = new Intent(Intent.ACTION_SEND);
                //sendEmail.setData(Uri.parse("mailto:"));
                sendEmail.setType("message/rfc822");
                sendEmail.putExtra(Intent.EXTRA_EMAIL, TO);
                //sendEmail.putExtra(Intent.EXTRA_CC, CC);
                sendEmail.putExtra(Intent.EXTRA_SUBJECT, "Test subject");
                sendEmail.putExtra(Intent.EXTRA_TEXT, "Test message");

                try {
                    startActivity(Intent.createChooser(sendEmail, "Send email..."));
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }
}
