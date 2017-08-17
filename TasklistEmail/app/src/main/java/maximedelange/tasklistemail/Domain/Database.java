package maximedelange.tasklistemail.Domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by M on 8/17/2017.
 */

public class Database extends SQLiteOpenHelper{

    // Fields
    private static final Integer DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasklist.db";

    // Database table.
    private static final String TABLE = "Task";
    private static final String taskID = "taskID";
    private static final String taskSubject = "taskSubject";
    private static final String taskMessage = "taskMessage";
    private static final String taskDate = "taskDate";

    public Database(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating database and table.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " + TABLE + "("
                + taskID + " integer primary key autoincrement,"
                + taskSubject + " text,"
                + taskMessage + " text,"
                + taskDate + "date" + ")";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop database table.
        sqLiteDatabase.execSQL("drop table if exists " + TABLE);

        // Creating database table again.
        onCreate(sqLiteDatabase);
    }

    // Methods
    /*
    Adding a task to the database.
     */
    public void addTask(Task task){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(taskSubject, task.getSubject());
        values.put(taskMessage, task.getMessage());

        database.insert(TABLE, null, values);
        database.close();
    }

    /*
    Retrieving all tasks from the database
     */
    public List<Task> getTasks(){
        List<Task> tasks = new ArrayList<>();
        Task task = null;
        // Select all Query.
        String sql = "select * from " + TABLE;

        SQLiteDatabase database = getWritableDatabase();
        // Executing query.
        Cursor cursor = database.rawQuery(sql, null);

        // Getting all values.
        if(cursor.moveToFirst()){
            do{
                task = new Task(cursor.getString(1), cursor.getString(2), new Date());
                task.setTaskID(Integer.parseInt(cursor.getString(0)));
                System.out.println("TASK ID: " + task.getTaskID());
                tasks.add(task);
            }while (cursor.moveToNext());
        }
        return tasks;
    }

    /*
    Removing a task.
     */
    public void removeTask(Integer id){
        Task task = null;
        String sql = "delete from " + TABLE + " where " + taskID + " = " + id;

        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
        database.close();
    }
}
