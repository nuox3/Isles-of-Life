package com.cs465.islesoflife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;

import com.cs465.islesoflife.Utils.DatabaseHandler;
import com.cs465.islesoflife.Model.IslandModel;
import androidx.appcompat.app.AppCompatActivity;

import net.penguincoders.doit.R;

import java.util.List;
import java.util.Objects;

public class IslandDetail  extends AppCompatActivity {

    private DatabaseHandler db;
    private List<IslandModel> islandList;
    private int islandListSize;
    int curIslandIdx = 0;

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

        final TextView islandName = (TextView) findViewById(R.id.islandName);
        final ImageView islandImage = (ImageView) findViewById(R.id.islandImage);
        final TextView islandLevel = (TextView) findViewById(R.id.level);
        final ProgressBar islandProgress = (ProgressBar)findViewById(R.id.progress);
        final TextView islandProgressText = (TextView)findViewById(R.id.levelText);

        if(curIslandIdx < islandListSize && curIslandIdx >= 0) {
            islandName.setText(islandList.get(curIslandIdx).getName());
            islandLevel.setText("Level: " + islandList.get(curIslandIdx).getLevel());
            islandProgress.setProgress(islandList.get(curIslandIdx).getEXP());
            islandProgressText.setText("(" + islandList.get(curIslandIdx).getEXP() + "/100)");
            int imageResource = getResources().getIdentifier(islandList.get(curIslandIdx).getImagePath(), null, getPackageName());
            islandImage.setImageResource(imageResource);

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
