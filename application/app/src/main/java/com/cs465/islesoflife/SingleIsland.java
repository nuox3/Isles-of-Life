package com.cs465.islesoflife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import 	android.widget.TextView;
import 	android.widget.ImageView;

import com.cs465.islesoflife.Utils.DatabaseHandler;
import com.cs465.islesoflife.Model.IslandModel;

import net.penguincoders.doit.R;

import java.util.List;
import java.util.Objects;

public class SingleIsland extends AppCompatActivity {

    private DatabaseHandler db;
    private List<IslandModel> islandList;
    private int islandListSize;
    int curIslandIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_island);
        Objects.requireNonNull(getSupportActionBar()).hide();

        db = new DatabaseHandler(this);
        db.openDatabase();

        Bundle b = getIntent().getExtras();
        if(b != null)
            curIslandIdx = b.getInt("curIdx");

        islandList = db.getAllIslands();
        islandListSize = islandList.size();

        final TextView islandName = (TextView) findViewById(R.id.islandName);
        final ImageView islandImage = (ImageView) findViewById(R.id.toIslandDetail);

        if(islandListSize > 0) {
            islandName.setText(islandList.get(curIslandIdx).getName());

            int imageResource = getResources().getIdentifier(islandList.get(curIslandIdx).getImagePath(), null, getPackageName());
            islandImage.setImageResource(imageResource);
        }

        // change image and text content
        ImageButton btn_to_left_island = (ImageButton) findViewById(R.id.toLeftIsland);
        ImageButton btn_to_right_island = (ImageButton) findViewById(R.id.toRightIsland);

        btn_to_left_island.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curIslandIdx > 0){
                    curIslandIdx--;
                    islandName.setText(islandList.get(curIslandIdx).getName());
                    int imageResource = getResources().getIdentifier(islandList.get(curIslandIdx).getImagePath(), null, getPackageName());
                    islandImage.setImageResource(imageResource);
                }
            }
        });

        btn_to_right_island.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curIslandIdx < islandListSize - 1){
                    curIslandIdx++;
                    islandName.setText(islandList.get(curIslandIdx).getName());
                    int imageResource = getResources().getIdentifier(islandList.get(curIslandIdx).getImagePath(), null, getPackageName());
                    islandImage.setImageResource(imageResource);
                }
            }
        });


        // switch to the other pages
        ImageButton btn_to_new_task = (ImageButton) findViewById(R.id.toDailyTask);
        ImageButton btn_to_island_detail = (ImageButton) findViewById(R.id.toIslandDetail);

        btn_to_new_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SingleIsland.this, DailyTaskActivity.class);
                startActivity(intent);
            }
        });

        btn_to_island_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                Bundle para = new Bundle();
                para.putInt("curIslandIdx", curIslandIdx);

                intent.setClass(SingleIsland.this, IslandDetail.class);
                intent.putExtras(para);

                startActivity(intent);
            }
        });

        ImageButton btn_to_home_page = (ImageButton) findViewById(R.id.toHomePage);

        btn_to_home_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}