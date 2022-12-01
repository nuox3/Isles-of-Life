package com.cs465.islesoflife;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cs465.islesoflife.Adapters.TasksOnDateAdapter;
import com.cs465.islesoflife.Model.ToDoModel;
import com.cs465.islesoflife.Utils.DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import net.penguincoders.doit.R;

import java.util.Collections;
import java.util.List;

public class DisplayTasksBasedOnDate extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";


    private DatabaseHandler db;
    private RecyclerView tasksRecyclerView;
    private TasksOnDateAdapter tasksAdapter;
    private List<ToDoModel> taskList;
    private static String date;



    public static DisplayTasksBasedOnDate newInstance(String s){
        date = s;
        return new DisplayTasksBasedOnDate();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tasks_date, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        boolean isUpdate = false;

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        tasksRecyclerView = getActivity().findViewById(R.id.tasksBasedOnDate);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tasksRecyclerView.setAdapter(tasksAdapter);


        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new TaskOnDateItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

//        System.out.println(monthYearFromDate(selectedDate).toString() + "-" + dayText);
        taskList = db.getAllTasksBasedOnDueDate(date);
        Collections.reverse(taskList);

        tasksAdapter.setTasks(taskList);




    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener)
            ((DialogCloseListener)activity).handleDialogClose(dialog);
    }

}