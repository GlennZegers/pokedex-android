package com.example.pokedex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class NameFromOtherAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new pokemonTaskName().execute();
        setContentView(R.layout.activity_name_from_other_app);

        Intent intent = getIntent();
        final String name = intent.getStringExtra(Intent.EXTRA_TEXT);

       TextView textViewName = (TextView) findViewById(R.id.textViewName);
       textViewName.setText("Select the pokemon you want to name '"+name+"'");

       Button saveNameButton = (Button) findViewById(R.id.saveNameButton);
       saveNameButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Spinner spinner = findViewById(R.id.pokemonSpinner);
               Intent intent = new Intent(NameFromOtherAppActivity.this, MainActivity.class);
               intent.putExtra(Intent.EXTRA_INDEX, spinner.getSelectedItemPosition() + 1);
               intent.putExtra(Intent.EXTRA_TEXT,name);
               startActivity(intent);
           }
       });
    }

    protected class pokemonTaskName extends AsyncTask<Void, Void, JSONObject>
    {
        @Override
        protected JSONObject doInBackground(Void... params)
        {

            String str="https://pokeapi.co/api/v2/pokemon?limit=50";
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            try
            {
                URL url = new URL(str);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(line);
                }

                return new JSONObject(stringBuffer.toString());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                return null;
            }
            finally
            {
                if(bufferedReader != null)
                {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(JSONObject response)
        {
            if(response != null)
            {
                try {
                    JSONArray responseArray = response.getJSONArray("results");
                    ArrayList<String> names = new ArrayList<String>();
                    for(int i =0; i< responseArray.length(); i++){
                        names.add(responseArray.getJSONObject(i).getString("name"));
                    }
                    Spinner pokemonSpinner = (Spinner) findViewById(R.id.pokemonSpinner);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            NameFromOtherAppActivity.this, android.R.layout.simple_spinner_item, names);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    pokemonSpinner.setAdapter(adapter);

                } catch (JSONException ex) {
                    Log.e("App", "Failure", ex);
                }
            }
        }
    }
}
