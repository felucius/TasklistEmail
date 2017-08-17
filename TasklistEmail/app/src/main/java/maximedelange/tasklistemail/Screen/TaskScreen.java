package maximedelange.tasklistemail.Screen;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import maximedelange.tasklistemail.Domain.Database;
import maximedelange.tasklistemail.Domain.Task;
import maximedelange.tasklistemail.R;

public class TaskScreen extends AppCompatActivity {

    // Fields
    private Spinner spinnerTasks = null;
    private TextView showTasks = null;
    private Database database = null;
    private List<Task> taskList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Additions
        removeActionBar();
        showTasks();
    }

    private void removeActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void showTasks(){
        database = new Database(this);
        taskList = database.getTasks();
        if(taskList.size() > 0){
            spinnerTasks = (Spinner) findViewById(R.id.spnTasks);
            ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, taskList);
            spinnerTasks.setAdapter(adapter);
            getSelectedTask();
        }else{
            System.out.println("No tasks available.");
        }
    }

    public void getSelectedTask(){
        showTasks = (TextView) findViewById(R.id.txtShowTask);

        // Get the selected item of the spinner and show it in a text view area.
        spinnerTasks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Task task = (Task) adapterView.getItemAtPosition(position);
                showTasks.setText("Date created:" + task.getDateCreated() + "\n\n" + "Message: " + task.getMessage());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
