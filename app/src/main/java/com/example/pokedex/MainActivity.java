package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements OverviewFragment.OnItemSelected{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onItemSelected(String item) {
        DetailFragment f = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detailFragment);
        f.setPokemon(item);
    }
}
