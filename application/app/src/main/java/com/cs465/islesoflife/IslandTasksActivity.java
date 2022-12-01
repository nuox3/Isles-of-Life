package com.cs465.islesoflife;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cs465.islesoflife.Adapters.TasksOnIslandAdapter;
import com.cs465.islesoflife.Adapters.ToDoAdapter;
import com.cs465.islesoflife.Model.ToDoModel;
import com.cs465.islesoflife.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.penguincoders.doit.R;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class IslandTasksActivity extends AppCompatActivity implements DialogCloseListener{

    private DatabaseHandler db;

    private RecyclerView tasksRecyclerView;
    private TasksOnIslandAdapter tasksAdapter;

    private List<ToDoModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_date);
        Objects.requireNonNull(getSupportActionBar()).hide();

        db = new DatabaseHandler(this);
        db.openDatabase();

        tasksRecyclerView = findViewById(R.id.tasksBasedOnDate);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        tasksAdapter = new TasksOnIslandAdapter(db, IslandTasksActivity.this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new TaskOnIslandItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);


        taskList = db.getAllTasks();
        Collections.reverse(taskList);

        tasksAdapter.setTasks(taskList);
    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}