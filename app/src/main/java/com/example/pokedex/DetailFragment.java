package com.example.pokedex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);;
        //setContentView(R.layout.overview_fragment
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.detail_fragment,container,false);
    }

    public void setPokemon(String pokemon){
        TextView t = getView().findViewById(R.id.Name);
        t.setText(pokemon);
    }

    public void setPokemonImage(int index)
    {

    }
}
