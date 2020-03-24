package com.example.pokedex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class DetailFragment extends Fragment {
    private int index = 0;
    private HashMap<Integer,String> nicknames = new HashMap<Integer,String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        try {
            File file = getContext().getFileStreamPath("nickname.ser");
            if(file != null && file.exists()) {
                FileInputStream fis = getContext().openFileInput("nickname.ser");
                ObjectInputStream is = new ObjectInputStream(fis);
                nicknames = (HashMap<Integer, String>) is.readObject();
                is.close();
                fis.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Button nameButton = view.findViewById(R.id.name_button);
        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index != 0){
                    Intent intent = new Intent(getContext(), NamingActivity.class);
                    intent.putExtra(Intent.EXTRA_INDEX, index);
                    startActivity(intent);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.detail_fragment,container,false);
    }

    public void setNickname(int index, String name) throws IOException {
        this.nicknames.put(index, name);
        FileOutputStream fos = getContext().openFileOutput("nickname.ser", Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(this.nicknames);
        os.close();
        fos.close();
    }

    public void setPokemon(String pokemon, int index){
        this.index = index;
        TextView t = getView().findViewById(R.id.Name);
        if(nicknames.containsKey(index)){
            t.setText(nicknames.get(index) + " (" + pokemon +")");
        }else{
            t.setText(pokemon);
        }
    }

    public void setPokemonImage(int index)
    {

    }
}
