package com.cs465.islesoflife;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import com.cs465.islesoflife.Model.ToDoModel;

import net.penguincoders.doit.R;

import com.cs465.islesoflife.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AddNewTask extends BottomSheetDialogFragment{

    public static final String TAG = "ActionBottomDialog";
    private EditText newTaskText;
    private Spinner newTaskCategory;
    private EditText newTaskDueDate;
    private EditText newTaskDueTime;
    private Button newTaskSaveButton;
    private RadioGroup radioGroup;
    private RadioButton newTaskImportance;

    private DatabaseHandler db;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    private int mYear, mMonth, mDay;
    int mHour, mMinute;

    private Spinner sp1;
    List<String> data;    //列表框的选项


    public static AddNewTask newInstance(){
        return new AddNewTask();
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

        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newTaskText = Objects.requireNonNull(getView()).findViewById(R.id.newTaskText);
        newTaskCategory = getView().findViewById(R.id.newTaskCategory);
        newTaskDueDate = getView().findViewById(R.id.newTaskDueDate);
//        newTaskDueDate = Objects.requireNonNull(getView()).findViewById(R.id.newTaskDueDate);
        newTaskDueTime = Objects.requireNonNull(getView()).findViewById(R.id.newTaskDueTime);
        newTaskSaveButton = getView().findViewById(R.id.newTaskButton);

        radioGroup = getView().findViewById(R.id.radioGroup_taskImportance);

        boolean isUpdate = false;

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        List<String> arrayList = db.getAllIslandNames();
        String[] data = (String[]) arrayList.toArray(new String[0]);
        // set spiner
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1, data);
        //获取到控件的属性
        sp1 = (Spinner)getView().findViewById(R.id.newTaskCategory);
        //将选项加入下拉框
        sp1.setAdapter(arrayAdapter);

        // set date picker
        newTaskDueDate.setOnTouchListener((currView, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(), R.style.MyDatePickerDialogTheme,
                        (view1, year, monthOfYear, dayOfMonth) -> {
                            newTaskDueDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            datePickerDialog.dismiss();
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
            return true;
        });

        // set time picker
        newTaskDueTime.setOnTouchListener((currView2, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(getActivity(),R.style.MyDatePickerDialogTheme,
                        (view2, hourOfDay, minute) -> {
                            newTaskDueTime.setText(hourOfDay + ":" + minute);
                            timePickerDialog.dismiss();
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
            return true;
        });

        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            newTaskText.setText(task);

//            String category = bundle.getString("category");
//            newTaskCategory.setText(category);

            String taskDueDate = bundle.getString("taskDueDate");
            newTaskDueDate.setText(taskDueDate);

            String taskDueTime = bundle.getString("taskDueTime");
            newTaskDueTime.setText(taskDueTime);

            String taskImportance = bundle.getString("importance");
            newTaskImportance.setText(taskImportance);
            assert task != null;
            if(task.length()>0)
                newTaskSaveButton.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimaryDark));
        }


        newTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newTaskSaveButton.setEnabled(false);
                    newTaskSaveButton.setTextColor(Color.GRAY);
                }
                else{
                    newTaskSaveButton.setEnabled(true);
                    newTaskSaveButton.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimaryDark));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final boolean finalIsUpdate = isUpdate;
        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newTaskText.getText().toString();
                String category = newTaskCategory.getSelectedItem().toString();
                String taskDueDate = newTaskDueDate.getText().toString();
                String taskDueTime = newTaskDueTime.getText().toString();

                newTaskImportance = (RadioButton) getView().findViewById(radioGroup.getCheckedRadioButtonId());
                String taskImportance = newTaskImportance.getText().toString();
                if(finalIsUpdate){
                    db.updateTask(bundle.getInt("id"), text);
                }
                else {
                    ToDoModel task = new ToDoModel();
                    task.setTask(text);
                    task.setStatus(0);
                    task.setCategory(category);
                    task.setTaskDueDate(taskDueDate);
                    task.setTaskDueTime(taskDueTime);
                    task.setImportance(taskImportance);
                    db.insertTask(task);
                }
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
