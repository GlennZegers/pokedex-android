package com.example.pokedex;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.MyViewHolder> {
    private String[] mDataset;
    private OverviewFragment.OnItemSelected listener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public MyViewHolder(TextView v) {
            super(v);
            textView = v;

        }
    }

    public PokemonAdapter(String[] myDataset, MainActivity m) {
        mDataset = myDataset;
        listener = m;
    }

    @Override
    public PokemonAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_text_view, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) v;
                listener.onItemSelected( textView.getText().toString());
            }
        });
        MyViewHolder vh = new MyViewHolder(v);
        vh.textView.setTextSize(20);
        vh.textView.setGravity(Gravity.CENTER_HORIZONTAL);
        vh.textView.setTypeface(null, Typeface.BOLD);
        vh.textView.setPadding(25,40,0,0);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
