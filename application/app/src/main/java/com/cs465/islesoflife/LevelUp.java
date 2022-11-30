package com.cs465.islesoflife;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cs465.islesoflife.Adapters.SpeciesChoiceAdapter;
import com.cs465.islesoflife.Model.SpeciesModel;
import com.cs465.islesoflife.Utils.DatabaseHandler;

import net.penguincoders.doit.R;

import java.util.List;
import java.util.Objects;

public class LevelUp extends AppCompatActivity implements SpeciesChoiceAdapter.AdapterCallback {
    RecyclerView recyclerView;
    private List<SpeciesModel> speciesList;
    private int speciesListSize;
    private DatabaseHandler db;
    private int curLevel;
    private int curIslandIdx;
    SpeciesChoiceAdapter speciesAdapter;
    private int curChoice;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_up);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Bundle b = getIntent().getExtras();
        if(b != null) {
            curLevel = b.getInt("curLevel");
            curIslandIdx = b.getInt("curIslandIdx");
        }

        db = new DatabaseHandler(this);
        db.openDatabase();

        speciesList = db.getAllSpeciesAtSameLevel(curLevel); //TODO
        speciesListSize = speciesList.size();

        curChoice = speciesList.get(0).getSpeciesId();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        speciesAdapter = new SpeciesChoiceAdapter(speciesList, this, LevelUp.this);
        recyclerView.setAdapter(speciesAdapter);

        Button confirm_button = (Button) findViewById(R.id.confirm);

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.insertContain(curIslandIdx, curChoice);
                finish();
            }
        });

    }

    @Override
    public void onItemClicked(int speciesId){
        curChoice = speciesId;
    }

}
