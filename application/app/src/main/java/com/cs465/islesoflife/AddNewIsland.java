package com.cs465.islesoflife;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ViewSwitcher;
import net.penguincoders.doit.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.cs465.islesoflife.Model.IslandModel;
import com.cs465.islesoflife.Model.ToDoModel;
import com.cs465.islesoflife.Utils.DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Collections;
import java.util.Objects;


public class AddNewIsland extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";

    private ImageView islandImage;
    private ImageButton nextIsland;
    private ImageButton previousIsland;
    private EditText newIslandName;
    private Button newIslandSaveButton;

    private DatabaseHandler db;

    private Integer ListImage[] = {R.drawable.sand_island_1, R.drawable.sand_island_2,
            R.drawable.sand_island_3, R.drawable.rock_island};

    private String baseArr[] = {"Sand Island", "Sand Island", "Sand Island", "Rock Island"};


    private Integer imageLength = ListImage.length;
    private  Integer counter = 0;

    public static AddNewIsland newInstance(){
        return new AddNewIsland();
    }

@Override
    public void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setStyle(STYLE_NORMAL, R.style.DialogStyle);

    db = new DatabaseHandler(getActivity());
    db.openDatabase();


}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_island, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newIslandName = Objects.requireNonNull(getView()).findViewById(R.id.newIslandName);
        newIslandSaveButton = getView().findViewById(R.id.ok_button);

        islandImage = getView().findViewById(R.id.imageSwitcher);
        islandImage.setImageResource(ListImage[counter]);


        nextIsland = getView().findViewById(R.id.nextIsland);
        nextIsland.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter < imageLength - 1){
                    counter++;
                    islandImage.setImageResource(ListImage[counter]);
                }
            }
        });

        previousIsland = getView().findViewById(R.id.previousIsland);
        previousIsland.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter > 0){
                    counter--;
                    islandImage.setImageResource(ListImage[counter]);
                }
            }
        });

        newIslandName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newIslandSaveButton.setEnabled(false);
                    newIslandSaveButton.setTextColor(Color.GRAY);
                }
                else{
                    newIslandSaveButton.setEnabled(true);
                    newIslandSaveButton.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimaryDark));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        newIslandSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String islandName = newIslandName.getText().toString();
                Integer islandLevel = 1;
                String islandBase = baseArr[counter];
                String islandImagePath = "";
                Integer islandId= db.getIslandNumber() + 1;
                if(counter == 0){
                    islandImagePath = "@drawable/sand_island_1";
                }else if(counter == 1){
                    islandImagePath = "@drawable/sand_island_2";
                }else if(counter == 2){
                    islandImagePath = "@drawable/sand_island_3";
                }else if(counter == 3){
                    islandImagePath = "@drawable/rock_island";
                }
                Integer islandEXP = 0;



                IslandModel island = new IslandModel();
                island.setName(islandName);
                island.setLevel(islandLevel);
                island.setEXP(islandEXP);
                island.setIslandId(islandId);
                island.setBase(islandBase);
                island.setImagePath(islandImagePath);
                db.insertIsland(island);

                dismiss();

            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener)
            ((DialogCloseListener)activity).handleDialogClose(dialog);
    }


}
