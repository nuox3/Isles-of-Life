package com.cs465.islesoflife;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.cs465.islesoflife.Adapters.TasksOnDateAdapter;
import com.cs465.islesoflife.Adapters.TasksOnIslandAdapter;

public class TaskOnIslandItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private TasksOnIslandAdapter adapter;

    public TaskOnIslandItemTouchHelper(TasksOnIslandAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {

    }


}