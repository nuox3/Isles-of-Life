package com.cs465.islesoflife.Adapters;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cs465.islesoflife.Model.IslandModel;
import com.cs465.islesoflife.Model.ToDoModel;
import com.cs465.islesoflife.SingleIsland;

import java.util.List;

import net.penguincoders.doit.R;

public class IslandAdapter extends RecyclerView.Adapter<IslandAdapter.ViewHolder> {

    List<IslandModel> islandList;
    private Context context;

    public IslandAdapter(List<IslandModel> islandList, Context context) {
        this.islandList = islandList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homepage_single_island, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int myPosition) {
        final int position = myPosition;
        holder.progress.setProgress(islandList.get(position).getEXP());
        holder.islandName.setText(islandList.get(position).getName());
        int imageResource = context.getResources().getIdentifier(islandList.get(position).getImagePath(), null, context.getPackageName());
        holder.islandImage.setImageResource(imageResource);
        holder.islandImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                Bundle para = new Bundle();
                para.putInt("curIdx", position);

                intent.setClass(context, SingleIsland.class);
                intent.putExtras(para);
                context.startActivity(intent);
            }
        });
        holder.islandLevel.setText("Lv " + islandList.get(position).getLevel());
    }

    //RecyclerView長度
    @Override
    public int getItemCount() {
        return islandList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progress;
        public TextView islandName;
        public ImageButton islandImage;
        public TextView islandLevel;


        public ViewHolder(View holder) {
            super(holder);
            progress = (ProgressBar) holder.findViewById(R.id.progress);
            islandName = (TextView) holder.findViewById(R.id.islandName);
            islandImage = (ImageButton) holder.findViewById(R.id.islandImage);
            islandLevel = (TextView) holder.findViewById(R.id.level);
        }
    }

    public void setIslands(List<IslandModel> islandList) {
        this.islandList = islandList;
        notifyDataSetChanged();
    }
}
