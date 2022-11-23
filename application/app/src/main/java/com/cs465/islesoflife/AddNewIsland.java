package com.cs465.islesoflife;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;
import net.penguincoders.doit.R;

import androidx.appcompat.app.AppCompatActivity;


public class AddNewIsland extends AppCompatActivity {
    private ImageView islandImage;
    private ImageButton nextIsland;
    private ImageButton previousIsland;

    private Integer ListImage[] = {R.drawable.sand_island_1, R.drawable.sand_island_2,
            R.drawable.sand_island_3, R.drawable.rock_island};

    private Integer imageLength = ListImage.length;
    private  Integer counter = 0;

@Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.new_island);

    islandImage = findViewById(R.id.imageSwitcher);
    islandImage.setImageResource(ListImage[counter]);


    nextIsland = findViewById(R.id.nextIsland);
    nextIsland.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(counter < imageLength - 1){
                counter++;
                islandImage.setImageResource(ListImage[counter]);
            }
        }
    });

    previousIsland = findViewById(R.id.previousIsland);
    previousIsland.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(counter > 0){
                counter--;
                islandImage.setImageResource(ListImage[counter]);
            }
        }
    });

}




}
