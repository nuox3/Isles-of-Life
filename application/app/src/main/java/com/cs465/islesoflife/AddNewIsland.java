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

import androidx.appcompat.app.AppCompatActivity;


public class AddNewIsland extends AppCompatActivity {
    private ImageSwitcher imageSwitcher;
    private ImageButton nextIsland;
    private ImageButton previousIsland;

    private Integer ListImage[] = {R.drawable.sand_island_1, R.drawable.sand_island_2,
            R.drawable.sand_island_3, R.drawable.rock_island};

    private Integer imageLength = ListImage.length;
    private  Integer counter = -1;

@Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.new_island);

    imageSwitcher = findViewById(R.id.imageSwitcher);
    nextIsland = findViewById(R.id.nextIsland);
    nextIsland.setOnClickListener((View.OnClickListener) this);

    previousIsland = findViewById(R.id.previousIsland);
    previousIsland.setOnClickListener((View.OnClickListener) this);

    imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
        @Override
        public View makeView() {
            ImageView imageView = new ImageView(getApplicationContext());

            imageSwitcher.setLayoutParams(new ViewGroup.LayoutParams
                    (ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));

            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageResource(R.drawable.sand_island_1);

            return imageView;
        }
    });

    Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
    Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);

    imageSwitcher.setOutAnimation(out);
    imageSwitcher.setInAnimation(in);
}

    public void onClick(View v) {

        counter ++;

        if (counter == imageLength) {
            imageSwitcher.setImageResource(ListImage[counter]);
        }
        else {
            imageSwitcher.setImageResource(ListImage[counter]);
        }
    }



}
