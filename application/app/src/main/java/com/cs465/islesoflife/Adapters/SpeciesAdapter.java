package com.cs465.islesoflife.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import net.penguincoders.doit.R;

public class SpeciesAdapter extends RecyclerView.Adapter<SpeciesAdapter.ViewHolder> {

    ArrayList<String> speciesList;

    public SpeciesAdapter(ArrayList<String> speciesList) {
        this.speciesList = speciesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_specie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.speciesName.setText(speciesList.get(position));
    }

    //RecyclerView長度
    @Override
    public int getItemCount() {
        return speciesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView speciesName;

        public ViewHolder(View holder) {
            super(holder);
            speciesName = (TextView) holder.findViewById(R.id.speciesName);
        }
    }
}