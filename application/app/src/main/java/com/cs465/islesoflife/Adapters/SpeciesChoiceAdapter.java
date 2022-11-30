package com.cs465.islesoflife.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cs465.islesoflife.Model.SpeciesModel;

import net.penguincoders.doit.R;

import java.util.List;

public class SpeciesChoiceAdapter extends RecyclerView.Adapter<SpeciesChoiceAdapter.ViewHolder>{
    List<SpeciesModel> speciesList;
    private Context context;
    private int currentChoice = 0;
    private int currentChoiceSpeciesIdx;

    public interface AdapterCallback{
        void onItemClicked(int speciesId);
    }

    AdapterCallback callback;

    public SpeciesChoiceAdapter(List<SpeciesModel> speciesList, Context context, AdapterCallback callback) {
        this.speciesList = speciesList;
        this.context = context;
        this.callback = callback;
    }


    @Override
    public SpeciesChoiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_specie_choice, parent, false);
        return new SpeciesChoiceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int myPosition) {
        final int position = myPosition;
        int imageResource = context.getResources().getIdentifier(speciesList.get(position).getImagePath(), null, context.getPackageName());
        holder.speciesImage.setImageResource(imageResource);

        if(myPosition == currentChoice)
            holder.border.setBackgroundResource(R.drawable.customborder);
        else
            holder.border.setBackground(null);

        holder.speciesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentChoice = position;
                currentChoiceSpeciesIdx = speciesList.get(currentChoice).getSpeciesId();
                // Log.d("123",String.valueOf(currentChoiceSpeciesIdx));
                if(callback != null) {
                    callback.onItemClicked(currentChoiceSpeciesIdx);
                }
                notifyDataSetChanged();
            }
        });
        holder.speciesName.setText(speciesList.get(position).getName());
    }

    //RecyclerView長度
    @Override
    public int getItemCount() {
        return speciesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton speciesImage;
        public TextView speciesName;
        public LinearLayout border;


        public ViewHolder(View holder) {
            super(holder);
            speciesImage = (ImageButton) holder.findViewById(R.id.species_src);
            speciesName = (TextView) holder.findViewById(R.id.species_name);
            border = (LinearLayout) holder.findViewById(R.id.toggle);
        }
    }



}
