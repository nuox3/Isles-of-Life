package com.cs465.islesoflife.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cs465.islesoflife.Model.ToDoModel;
import com.cs465.islesoflife.Model.IslandModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 10;
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

    private static final String ISLAND_TABLE = "island";
    private static final String ISLAND_ID = "islandId";
    private static final String ISLAND_NAME = "name";
    private static final String ISLAND_LEVEL = "level";
    private static final String ISLAND_BASE = "base";
    private static final String ISLAND_IMAGE_PATH = "imagePath";
    private static final String ISLAND_EXP = "exp";

    private static final String CREATE_ISLAND_TABLE = "CREATE TABLE " + ISLAND_TABLE + "(" + ISLAND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ISLAND_NAME + " TEXT, "
            + ISLAND_LEVEL + " INTEGER, " +  ISLAND_BASE + " TEXT, " + ISLAND_EXP +" INTEGER, " + ISLAND_IMAGE_PATH + " TEXT)";


    private SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    public void insertDefaultIsland(){
        ContentValues cv = new ContentValues();
        cv.put(ISLAND_NAME, "Fitness Island");
        cv.put(ISLAND_LEVEL, 3);
        cv.put(ISLAND_BASE, "Rock Island");
        cv.put(ISLAND_IMAGE_PATH, "@drawable/island_example2");
        cv.put(ISLAND_EXP, "25");
        db.insert(ISLAND_TABLE, null, cv);

        cv = new ContentValues();
        cv.put(ISLAND_NAME, "Happiness Island");
        cv.put(ISLAND_LEVEL, 2);
        cv.put(ISLAND_BASE, "Sand Island");
        cv.put(ISLAND_IMAGE_PATH, "@drawable/island_example");
        cv.put(ISLAND_EXP, "97");
        db.insert(ISLAND_TABLE, null, cv);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
        db.execSQL(CREATE_ISLAND_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ISLAND_TABLE);
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

    public List<IslandModel> getAllIslands(){
        List<IslandModel> islandList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(ISLAND_TABLE, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        IslandModel island = new IslandModel();
                        island.setIslandId(cur.getInt(cur.getColumnIndex(ISLAND_ID)));
                        island.setLevel(cur.getInt(cur.getColumnIndex(ISLAND_LEVEL)));
                        island.setName(cur.getString(cur.getColumnIndex(ISLAND_NAME)));
                        island.setBase(cur.getString(cur.getColumnIndex(ISLAND_BASE)));
                        island.setImagePath(cur.getString(cur.getColumnIndex(ISLAND_IMAGE_PATH)));
                        island.setEXP(cur.getInt(cur.getColumnIndex(ISLAND_EXP)));
                        islandList.add(island);
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
        return islandList;
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
