package com.cs465.islesoflife;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;

import com.cs465.islesoflife.Utils.DatabaseHandler;
import com.cs465.islesoflife.Model.IslandModel;
import com.cs465.islesoflife.Model.SpeciesModel;
import com.cs465.islesoflife.Adapters.SpeciesNameAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import net.penguincoders.doit.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IslandDetail  extends AppCompatActivity {

    private DatabaseHandler db;

    private List<IslandModel> islandList;
    private int islandListSize;

    int curIslandIdx = 0;
    private List<SpeciesModel> speciesList;
    private int speciesListSize;
    SpeciesNameAdapter speciesNameAdapter;
    RecyclerView recyclerView;
    ArrayList<String> speciesNameList = new ArrayList();

    protected void setIslandView(){
        final ImageView islandImage = (ImageView) findViewById(R.id.toIslandDetail);
        final ImageView Species234 = (ImageView) findViewById(R.id.level234);
        // TODO: species569
        // TODO: species7
        // TODO: species8
        // TODO: species10

        int imageResource = getResources().getIdentifier(islandList.get(curIslandIdx).getImagePath(), null, getPackageName());
        islandImage.setImageResource(imageResource);

        int curIslandLevel = islandList.get(curIslandIdx).getLevel();

        int speciesResource;

        switch (curIslandLevel) {
            case 2:
                if(speciesListSize != 0) {
                    speciesResource = getResources().getIdentifier(speciesList.get(0).getImagePath(), null, getPackageName());
                    Species234.setImageResource(speciesResource);
                }
                break;
            case 3:
                speciesResource = getResources().getIdentifier(speciesList.get(1).getImagePath(), null, getPackageName());
                Species234.setImageResource(speciesResource);
                break;
            case 4:
                speciesResource = getResources().getIdentifier(speciesList.get(2).getImagePath(), null, getPackageName());
                Species234.setImageResource(speciesResource);
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            default:
                break;
        }

    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.island_detail);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Bundle b = getIntent().getExtras();
        if(b != null)
            curIslandIdx = b.getInt("curIslandIdx");

        db = new DatabaseHandler(this);
        db.openDatabase();

        islandList = db.getAllIslands();
        islandListSize = islandList.size();

        speciesList = db.getAllSpeciesOnIsland(islandList.get(curIslandIdx).getIslandId());
        speciesListSize = speciesList.size();

        final TextView islandName = (TextView) findViewById(R.id.islandName);
        final TextView islandLevel = (TextView) findViewById(R.id.level);
        final ProgressBar islandProgress = (ProgressBar)findViewById(R.id.progress);
        final TextView islandProgressText = (TextView)findViewById(R.id.levelText);
        recyclerView = findViewById(R.id.recyclerView);


        if(curIslandIdx < islandListSize && curIslandIdx >= 0) {
            islandName.setText(islandList.get(curIslandIdx).getName());
            islandLevel.setText("Level: " + islandList.get(curIslandIdx).getLevel());
            islandProgress.setProgress(islandList.get(curIslandIdx).getEXP());
            islandProgressText.setText("(" + islandList.get(curIslandIdx).getEXP() + "/100)");
            setIslandView();

            speciesNameList.add("Island Base: " + String.valueOf(islandList.get(curIslandIdx).getBase()));
            for(int i = 0;i < speciesListSize;i++){
                speciesNameList.add(String.valueOf(speciesList.get(i).getName()));
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            speciesNameAdapter = new SpeciesNameAdapter(speciesNameList);
            recyclerView.setAdapter(speciesNameAdapter);
        }

        ImageButton btn_to_single_island = (ImageButton) findViewById(R.id.toSingleIsland);

        btn_to_single_island.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
