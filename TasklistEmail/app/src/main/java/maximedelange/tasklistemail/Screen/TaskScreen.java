package maximedelange.tasklistemail.Screen;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import maximedelange.tasklistemail.Domain.Database;
import maximedelange.tasklistemail.Domain.Task;
import maximedelange.tasklistemail.R;

public class TaskScreen extends AppCompatActivity {

    // Fields
    private Spinner spinnerTasks = null;
    private Context context = this;
    private Button removeTask = null;
    private TextView showTasks = null;
    private Database database = null;
    private List<Task> taskList = null;
    private Task selectedTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Additions
        removeActionBar();
        showTasks();
        removeTask();
    }

    private void removeActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void showTasks(){
        database = new Database(this);
        taskList = database.getTasks();
        if(taskList.size() > 0){
            getTasks();
            getSelectedTask();
        }else{
            System.out.println("No tasks available.");
        }
    }

    public void getTasks(){
        spinnerTasks = (Spinner) findViewById(R.id.spnTasks);
        ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(getApplicationContext(),
                android.R.layout.simple_spinner_item, taskList);
        spinnerTasks.setAdapter(adapter);

        if(taskList.size() == 0){
            if(showTasks != null && spinnerTasks != null){
                showTasks.setText("Nothing to see here...");
            }
        }
    }

    public void getSelectedTask(){
        showTasks = (TextView) findViewById(R.id.txtShowTask);

        // Get the selected item of the spinner and show it in a text view area.
        spinnerTasks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedTask = (Task) adapterView.getItemAtPosition(position);
                showTasks.setText("Date created:" + selectedTask.getDateCreated() + "\n\n" + "Message: " + selectedTask.getMessage());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing.
            }
        });
    }

    private void removeTask(){
        removeTask = (Button) findViewById(R.id.btnRemoveTask);
        removeTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTask != null){
                    taskList = new ArrayList<Task>();
                    database = new Database(context);
                    // Removing the selected task.
                    database.removeTask(selectedTask.getTaskID());
                    // Recalling the tasks from the database after deletion.
                    taskList = database.getTasks();
                    getTasks();
                }else{
                    System.out.println("No task is selected");
                }
            }
        });
    }
}
