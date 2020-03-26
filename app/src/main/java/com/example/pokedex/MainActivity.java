package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements OverviewFragment.OnItemSelected{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if(getIntent().hasExtra(getIntent().EXTRA_TEXT)){
            try {
                this.sendNickname(getIntent().getStringExtra(Intent.EXTRA_TEXT), getIntent().getIntExtra(Intent.EXTRA_INDEX,0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemSelected(String item, int index) {
        DetailFragment f = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detailFragment);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean test = sharedPreferences.getBoolean("switch", true);
        if(test)
        {
            f.setImage(index);
        }
        else
        {
            f.removeImage();
        }
        f.setPokemon(item, index);
    }

    public void sendNickname(String name, int index) throws IOException {
        DetailFragment f = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detailFragment);
        f.setNickname(index, name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem i =  menu.findItem(R.id.toolbar_settings);
        i.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent sendIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(sendIntent);
                return false;
            }
        });

        return true;

    }
}
