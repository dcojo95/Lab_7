package com.example.lab_7;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.lab_7.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    String characterName;
    String characterHeight;
    String characterMass;

    ArrayList<HashMap<String, String>> starWars;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        starWars = new ArrayList<>();
        listView = findViewById(R.id.listview);

        GetData getData = new GetData();
        getData.execute();
        



    }


    @SuppressLint("StaticFieldLeak")
    public class GetData extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings){
            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    String JSON_URL = "https://swapi.dev/api/people/?format=json";
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(in);

                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return current;

        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONArray jsonArray = new JSONArray(s);

                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                    characterName = jsonObject1.getString("name");
                    characterHeight = jsonObject1.getString("height");
                    characterMass = jsonObject1.getString("mass");


                    HashMap<String, String> characters = new HashMap<>();
                    characters.put("name", characterName);
                    characters.put("height", characterHeight);
                    characters.put("mass", characterMass);

                    starWars.add(characters);



                }
            }catch (Exception e){
                e.printStackTrace();
            }
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this,
                    starWars,
                    R.layout.fragment_details,
                    new String[]{"name", "height", "mass"},
                    new int[]{R.id.fill_me1, R.id.fill_me2, R.id.fill_me3});

            listView.setAdapter(adapter);



        }
    }


}






