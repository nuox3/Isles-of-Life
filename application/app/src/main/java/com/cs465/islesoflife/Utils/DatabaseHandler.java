package com.cs465.islesoflife.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cs465.islesoflife.Model.ToDoModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 5;
    private static final String NAME = "toDoListDatabase";
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String CATEGORY = "category";
    private static final String STATUS = "status";
    private static final String IMPORTANCE = "importance";
    private static final String TASK_CREATED_DATE = "taskCreatedDate";
    private static final String TASK_DUE_DATE = "taskDueDate";
    private static final String TASK_DUE_TIME = "taskDueTime";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, "
            + CATEGORY + " TEXT, " +  IMPORTANCE + " TEXT, " + TASK_CREATED_DATE + " TEXT, " + TASK_DUE_DATE + " TEXT, " + TASK_DUE_TIME + " TEXT, " + STATUS + " INTEGER)";

    private SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertTask(ToDoModel task){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(CATEGORY, task.getCategory());
        cv.put(STATUS, 0);
        cv.put(IMPORTANCE, task.getImportance());
        cv.put(TASK_CREATED_DATE, dateFormat.format(date));
        cv.put(TASK_DUE_DATE, task.getTaskDueDate());
        cv.put(TASK_DUE_TIME, task.getTaskDueTime());
        db.insert(TODO_TABLE, null, cv);
    }

    public List<ToDoModel> getAllTasks(){
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(TODO_TABLE, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        ToDoModel task = new ToDoModel();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setTask(cur.getString(cur.getColumnIndex(TASK)));
                        task.setCategory(cur.getString(cur.getColumnIndex(CATEGORY)));
                        task.setImportance(cur.getString(cur.getColumnIndex(IMPORTANCE)));
                        task.setTaskCreatedDate(cur.getString(cur.getColumnIndex(TASK_CREATED_DATE)));
                        task.setTaskDueDate(cur.getString(cur.getColumnIndex(TASK_DUE_DATE)));
                        task.setTaskDueTime(cur.getString(cur.getColumnIndex(TASK_DUE_TIME)));
                        task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        taskList.add(task);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }

    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateTask(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateCategory(int id, String category) {
        ContentValues cv = new ContentValues();
        cv.put(CATEGORY, category);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateTaskCreatedDate(int id, String date) {
        ContentValues cv = new ContentValues();
        cv.put(TASK_CREATED_DATE, date);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateImportance(int id, int importance) {
        ContentValues cv = new ContentValues();
        cv.put(CATEGORY, importance);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(TODO_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
    }
}
