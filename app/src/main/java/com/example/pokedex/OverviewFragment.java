package com.example.pokedex;

import androidx.fragment.app.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class OverviewFragment extends Fragment {
    OnItemSelected listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);;
        new pokemonTask().execute();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.overview_fragment,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listener = (MainActivity) getActivity();
    }

    protected class pokemonTask extends AsyncTask<Void, Void, JSONObject>
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
                Log.e("App", "yourDataTask", ex);
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
                    String[] myStringArray = new String[50];
                    for(int i =0; i< responseArray.length(); i++){
                        myStringArray[i] = responseArray.getJSONObject(i).getString("name");
                    }
                    PokemonAdapter pokemonAdapter = new PokemonAdapter(myStringArray, (MainActivity) getActivity());
                    RecyclerView list = (RecyclerView) getView().findViewById(R.id.list_view);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

                    list.setLayoutManager(layoutManager);
                    list.setAdapter(pokemonAdapter);
                } catch (JSONException ex) {
                    Log.e("App", "Failure", ex);
                }
            }
        }
    }


    interface OnItemSelected{
        public void onItemSelected(String item, int index);
    }
}
