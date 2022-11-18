package com.cs465.islesoflife.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cs465.islesoflife.Model.ToDoModel;
import com.cs465.islesoflife.Model.IslandModel;
import com.cs465.islesoflife.Model.SpeciesModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database information
    private static final int VERSION = 14;
    private static final String NAME = "toDoListDatabase";

    // Todo_task information
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

    // Island information
    private static final String ISLAND_TABLE = "island";
    private static final String ISLAND_ID = "islandId";
    private static final String ISLAND_NAME = "name";
    private static final String ISLAND_LEVEL = "level";
    private static final String ISLAND_BASE = "base";
    private static final String ISLAND_IMAGE_PATH = "imagePath";
    private static final String ISLAND_EXP = "exp";

    private static final String CREATE_ISLAND_TABLE = "CREATE TABLE " + ISLAND_TABLE + "(" + ISLAND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ISLAND_NAME + " TEXT, "
            + ISLAND_LEVEL + " INTEGER, " +  ISLAND_BASE + " TEXT, " + ISLAND_EXP +" INTEGER, " + ISLAND_IMAGE_PATH + " TEXT)";

    // Species Information
    private static final String SPECIES_TABLE = "species";
    private static final String SPECIES_ID = "speciesId";
    private static final String SPECIES_NAME = "name";
    private static final String SPECIES_LEVEL = "level";
    private static final String SPECIES_IMAGE_PATH = "imagePath";
    private static final String SPECIES_ISLAND_ID = "islandId";

    private static final String CREATE_SPECIES_TABLE = "CREATE TABLE " + SPECIES_TABLE + "(" + SPECIES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SPECIES_NAME + " TEXT, "
            + SPECIES_LEVEL + " INTEGER, " + SPECIES_ISLAND_ID +" INTEGER, " + SPECIES_IMAGE_PATH + " TEXT)";

    private SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    public void insertDefaultData(){
        db.delete(ISLAND_TABLE, null, null);
        ContentValues cv_island1 = new ContentValues();
        cv_island1.put(ISLAND_NAME, "Fitness Island");
        cv_island1.put(ISLAND_LEVEL, 3);
        cv_island1.put(ISLAND_BASE, "Rock Island");
        cv_island1.put(ISLAND_IMAGE_PATH, "@drawable/island_example2");
        cv_island1.put(ISLAND_EXP, "25");
        db.insert(ISLAND_TABLE, null, cv_island1);

        ContentValues cv_island2 = new ContentValues();
        cv_island2.put(ISLAND_NAME, "Happiness Island");
        cv_island2.put(ISLAND_LEVEL, 2);
        cv_island2.put(ISLAND_BASE, "Sand Island");
        cv_island2.put(ISLAND_IMAGE_PATH, "@drawable/island_example");
        cv_island2.put(ISLAND_EXP, "97");
        db.insert(ISLAND_TABLE, null, cv_island2);

        db.delete(SPECIES_TABLE, null, null);
        ContentValues cv_species1 = new ContentValues();
        cv_species1.put(SPECIES_NAME, "Black Egg");
        cv_species1.put(SPECIES_LEVEL, 2);
        cv_species1.put(SPECIES_IMAGE_PATH, "@drawable/island_egg_black");
        cv_species1.put(SPECIES_ISLAND_ID, "1");
        db.insert(SPECIES_TABLE, null, cv_species1);

        ContentValues cv_species2 = new ContentValues();
        cv_species2.put(SPECIES_NAME, "Purple Egg");
        cv_species2.put(SPECIES_LEVEL, 2);
        cv_species2.put(SPECIES_IMAGE_PATH, "@drawable/island_egg_purple");
        cv_species2.put(SPECIES_ISLAND_ID, "2");
        db.insert(SPECIES_TABLE, null, cv_species2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
        db.execSQL(CREATE_ISLAND_TABLE);
        db.execSQL(CREATE_SPECIES_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ISLAND_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SPECIES_TABLE);
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

    // TODO
    public List<SpeciesModel> getAllSpecies(int islandId){
        List<SpeciesModel> speciesList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            final String GET_ALL_SPECIES_QUERY = "SELECT * FROM " + SPECIES_TABLE + " WHERE islandId=" + islandId;
            cur = db.rawQuery(GET_ALL_SPECIES_QUERY, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        SpeciesModel species = new SpeciesModel();
                        species.setSpeciesId(cur.getInt(cur.getColumnIndex(SPECIES_ID)));
                        species.setIslandId(cur.getInt(cur.getColumnIndex(ISLAND_ID)));
                        species.setLevel(cur.getInt(cur.getColumnIndex(SPECIES_LEVEL)));
                        species.setName(cur.getString(cur.getColumnIndex(SPECIES_NAME)));
                        species.setImagePath(cur.getString(cur.getColumnIndex(SPECIES_IMAGE_PATH)));

                        speciesList.add(species);
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
        return speciesList;
    }

    public int getIslandEXP(String islandName){
        Cursor cur = null;
        db.beginTransaction();
        int exp = 0;
        try{
            final String GET_ALL_SPECIES_QUERY = "SELECT * FROM " + ISLAND_TABLE + " WHERE name=\"" + islandName+"\"";
//            System.out.println();
            cur = db.rawQuery(GET_ALL_SPECIES_QUERY, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        exp = cur.getInt(cur.getColumnIndex(ISLAND_EXP));
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

        return exp;
    }



    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateEXP(String name, int exp){
        ContentValues cv = new ContentValues();
        cv.put(ISLAND_EXP, exp);
        db.update(ISLAND_TABLE, cv, ISLAND_NAME + "= ?", new String[] {String.valueOf(name)});

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
