package com.cs465.islesoflife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cs465.islesoflife.Adapters.IslandAdapter;
import com.cs465.islesoflife.Model.IslandModel;
import com.cs465.islesoflife.Utils.DatabaseHandler;


import net.penguincoders.doit.R;

import java.util.List;
import java.util.Objects;

public class Homepage extends AppCompatActivity {

    RecyclerView recyclerView;
    private List<IslandModel> islandList;
    private int islandListSize;
    private DatabaseHandler db;
    IslandAdapter islandAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        Objects.requireNonNull(getSupportActionBar()).hide();

        db = new DatabaseHandler(this);
        db.openDatabase();


        islandList = db.getAllIslands();
        islandListSize = islandList.size();

        if(islandListSize == 0){
            db.insertDefaultData();
            islandList = db.getAllIslands();
            islandListSize = islandList.size();
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        islandAdapter = new IslandAdapter(islandList, this);
        recyclerView.setAdapter(islandAdapter);

        ImageButton btn_to_todolist = (ImageButton) findViewById(R.id.toTodoList);

        btn_to_todolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Homepage.this, DailyTaskActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btn_to_calendar = (ImageButton) findViewById(R.id.toCalendar);

        btn_to_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Homepage.this, CalendarViewActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btn_to_addnewisland = (ImageButton) findViewById(R.id.toAddIsland);

        btn_to_addnewisland.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Homepage.this, AddNewIsland.class);
                startActivity(intent);
            }
        });
    }
}
