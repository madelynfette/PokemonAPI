package com.example.Assignment5;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText searchInput;
    TextView nameTV;
    ListView listView;
    ArrayList<Pokemon> pokelist;
    Button clearButton;
    TextView weightTV;
    TextView heightTV;
    TextView basexpTV;
    TextView moveTV;
    TextView idTV;
    TextView abilityTV;
    Button searchButton;
    String name;
    Integer weight;
    Integer height;
    Integer baseXP;
    Integer pokeID;
    String moveName;
    String abilityName;
    String imageURL;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchInput = findViewById(R.id.search_input);
        nameTV = findViewById(R.id.nameTV);
        weightTV = findViewById(R.id.weightTV);
        heightTV = findViewById(R.id.heightTV);
        basexpTV = findViewById(R.id.basexpTV);
        idTV = findViewById(R.id.numberTV);
        moveTV = findViewById(R.id.moveTV);
        abilityTV = findViewById(R.id.abilityTV);
        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(searchListener);
        clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener(clearListener);
        listView = findViewById(R.id.list_view);
        pokelist = new ArrayList<>();
        AndroidNetworking.initialize(getApplicationContext());
        listView.setOnItemClickListener(listListener);
        imageView = findViewById(R.id.imageView);

    }

        //make initial request that adds pokemon to the list
        public void makeRequest(String id){
            AndroidNetworking.get("https://pokeapi.co/api/v2/pokemon/{name}/")
                    .addPathParameter("name", id)
                    .build()
                    .getAsObject(Pokemon.class, new ParsedRequestListener<Pokemon>() {

                        @Override
                        public void onResponse(Pokemon response) {
                            nameTV.setText(response.getName().toUpperCase());
                            name = response.getName();
                            idTV.setText(response.getID().toString());
                            pokeID = response.getID();
                            weightTV.setText(response.getWeight().toString());
                            weight = response.getWeight();
                            heightTV.setText(response.getHeight().toString());
                            height = response.getHeight();
                            basexpTV.setText(response.getBase_experience().toString());
                            baseXP = response.getBase_experience();
                         }

                        @Override
                        public void onError(ANError error) {
                            Toast.makeText(getApplicationContext(), "Not working", Toast.LENGTH_SHORT).show();
                        }
                    });

            AndroidNetworking.get("https://pokeapi.co/api/v2/pokemon/{name}/")
                    .addPathParameter("name", id)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                moveName =
                                jsonObject.getJSONArray("moves").getJSONObject(0).getJSONObject("move").getString("name");
                                moveTV.setText(moveName);
                                abilityName =
                                        jsonObject.getJSONArray("abilities").getJSONObject(0).getJSONObject("ability").getString("name");
                                abilityTV.setText(abilityName);
                                JSONObject json = new JSONObject(jsonObject.toString());
                                JSONObject sprites = json.getJSONObject("sprites");
                                String imageURL = sprites.getString("front_default");
                                Picasso.get().load(imageURL).into(imageView);
                                addToList();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });

        }


    //take in the search input and verify then API call if valid
        View.OnClickListener searchListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searched = searchInput.getText().toString();
                boolean valid = !searched.contains("%");

                //make sure it does not contain the listed symbols
                if (searched.contains("&")){
                    valid = false;
                }
                if (searched.contains("*")){
                    valid = false;
                }if (searched.contains("(")){
                    valid = false;
                }if (searched.contains(")")){
                    valid = false;
                }if (searched.contains("@")){
                    valid = false;
                }if (searched.contains("!")){
                    valid = false;
                }if (searched.contains(";")){
                    valid = false;
                }
                if (searched.contains(":")){
                    valid = false;
                }
                if (searched.contains("<")){
                    valid = false;
                }
                if (searched.contains(">")){
                    valid = false;
                }
                if(searched.contains("[0-9]+")){
                    int searchedInt = Integer.parseInt(searched);
                    if(searchedInt < 0 || searchedInt > 1010){
                        valid = false;
                    }}
                if(!valid){
                    Toast.makeText(getApplicationContext(), "The pokemon entered is invalid", Toast.LENGTH_SHORT).show();
                }
                if(valid){
                    makeRequest(searched);

                }

                }



        };

    //load the clicked pokemon from the list
        AdapterView.OnItemClickListener listListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //pull up the contents of the clicked pokemon from the listview
                Pokemon clickedPokemon = (Pokemon) adapterView.getItemAtPosition(i);
                String clickedName = clickedPokemon.getName();
                getPokemonInformation(clickedName);

            }
        };


        //removes current pokemon profile and search bar
        View.OnClickListener clearListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameTV.setText("");
                idTV.setText("");
                heightTV.setText("");
                weightTV.setText("");
                basexpTV.setText("");
                moveTV.setText("");
                abilityTV.setText("");
                searchInput.setText("");
            }
        };

        //creates a pokemon object and adds it to the list
        public void addToList(){
            Pokemon addedPokemon = new Pokemon(name, weight, height, baseXP, pokeID, moveName, abilityName, imageURL);
                pokelist.add(addedPokemon);
                if(listView.getAdapter() != null){
                    ((ArrayAdapter<Pokemon>) listView.getAdapter()).notifyDataSetChanged();
                }else{
                ArrayAdapter<Pokemon> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, pokelist);
                listView.setAdapter(adapter);}
        }

        //makes a call to get the pookemon information wihtout adding to list
    public void getPokemonInformation(String id){
        AndroidNetworking.get("https://pokeapi.co/api/v2/pokemon/{name}/")
                .addPathParameter("name", id)
                .build()
                .getAsObject(Pokemon.class, new ParsedRequestListener<Pokemon>() {

                    @Override
                    public void onResponse(Pokemon response) {
                        nameTV.setText(response.getName().toUpperCase());
                        name = response.getName();
                        idTV.setText(response.getID().toString());
                        pokeID = response.getID();
                        weightTV.setText(response.getWeight().toString());
                        weight = response.getWeight();
                        heightTV.setText(response.getHeight().toString());
                        height = response.getHeight();
                        basexpTV.setText(response.getBase_experience().toString());
                        baseXP = response.getBase_experience();
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getApplicationContext(), "Not working", Toast.LENGTH_SHORT).show();
                    }
                });

        AndroidNetworking.get("https://pokeapi.co/api/v2/pokemon/{name}/")
                .addPathParameter("name", id)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            moveName =
                                    jsonObject.getJSONArray("moves").getJSONObject(0).getJSONObject("move").getString("name");
                            moveTV.setText(moveName);
                            abilityName =
                                    jsonObject.getJSONArray("abilities").getJSONObject(0).getJSONObject("ability").getString("name");
                            abilityTV.setText(abilityName);
                            JSONObject json = new JSONObject(jsonObject.toString());
                            JSONObject sprites = json.getJSONObject("sprites");
                            String imageURL = sprites.getString("front_default");
                            Picasso.get().load(imageURL).into(imageView);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }


}