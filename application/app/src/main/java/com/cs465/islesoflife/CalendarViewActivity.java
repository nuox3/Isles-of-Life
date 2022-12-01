package com.cs465.islesoflife;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cs465.islesoflife.Adapters.CalendarAdapter;
import com.cs465.islesoflife.Adapters.TasksOnDateAdapter;
import com.cs465.islesoflife.Adapters.ToDoAdapter;
import com.cs465.islesoflife.Model.ToDoModel;
import com.cs465.islesoflife.Utils.DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.penguincoders.doit.R;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalendarViewActivity extends AppCompatActivity implements DialogCloseListener, CalendarAdapter.OnItemListener{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;

    private BottomSheetDialog mDialog;
    private DatabaseHandler db;
    private RecyclerView tasksRecyclerView;
    private TasksOnDateAdapter tasksAdapter;
    private List<ToDoModel> taskList;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();

        ImageButton btn_to_home = (ImageButton) findViewById(R.id.toHome);

        btn_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CalendarViewActivity.this, Homepage.class);
                startActivity(intent);
            }
        });
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        return date.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction(View view)
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View view)
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, String dayText)
    {
        if(!dayText.equals(""))
        {
//            DisplayTasksBasedOnDate.newInstance(monthYearFromDate(selectedDate).toString() + "-" + dayText).show(getSupportFragmentManager(), DisplayTasksBasedOnDate.TAG);
//            mDialog  = new BottomSheetDialog(this);
            View view = getLayoutInflater().inflate(R.layout.tasks_date, null);
//            mDialog.setContentView(R.layout.tasks_date);
            setContentView(view);

            db = new DatabaseHandler(this);
            db.openDatabase();

            tasksRecyclerView = findViewById(R.id.tasksBasedOnDate);
            tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            tasksAdapter = new TasksOnDateAdapter(db, CalendarViewActivity.this);
            tasksRecyclerView.setAdapter(tasksAdapter);


            ItemTouchHelper itemTouchHelper = new
                    ItemTouchHelper(new TaskItemTouchHelper(tasksAdapter));
            itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

            System.out.println(monthYearFromDate(selectedDate).toString() + "-" + dayText);
            taskList = db.getAllTasksBasedOnDueDate(monthYearFromDate(selectedDate).toString() + "-" + dayText);
            Collections.reverse(taskList);

            tasksAdapter.setTasks(taskList);

            ImageButton btn_to_calendar = (ImageButton) findViewById(R.id.toCalendar);

            btn_to_calendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(CalendarViewActivity.this, CalendarViewActivity.class);
                    startActivity(intent);
                }
            });
//            mDialog.show();


//            AlertDialog.Builder builder=new AlertDialog.Builder(this);
//            builder.setTitle(selectedDate + "\'s tasks");
////            builder.setMessage("提示内容");
//            tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
//            tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//            builder.setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            });
//            AlertDialog dialog=builder.create();
//            dialog.show();

//            String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
//            System.out.println(selectedDate);
//            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {

    }

}
