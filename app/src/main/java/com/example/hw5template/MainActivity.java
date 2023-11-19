package com.example.hw5template;

import androidx.appcompat.app.AppCompatActivity;
import com.androidnetworking.*;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText searchInput;
    ListView listView;
    LinkedList pokemonList;
    TextView name;
    TextView weight;
    TextView height;
    TextView basexp;
    TextView move;
    TextView idTV;
    TextView ability;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchInput = findViewById(R.id.search_input);
        name = findViewById(R.id.nameTV);
        weight = findViewById(R.id.weightTV);
        height = findViewById(R.id.heightTV);
        basexp = findViewById(R.id.basexpTV);
        idTV = findViewById(R.id.numberTV);
        move = findViewById(R.id.moveTV);
        ability = findViewById(R.id.abilityTV);
        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(searchListener);
        listView = findViewById(R.id.list_view);
        AndroidNetworking.initialize(getApplicationContext());

    }

        private void makeRequest(String id){
            ANRequest req = AndroidNetworking.get("https://pokeapi.co/api/v2/pokemon/{id or name}/")
                    .addPathParameter("id", id)
                    .setPriority(Priority.HIGH)
                    .build();
            req.getAsObjectList(Pokemon.class, new ParsedRequestListener<List<Pokemon>>() {
                @Override
                public void onResponse(List<Pokemon> pokemons) {
                    String TAG = "POKEMON";
                    Log.i(TAG, "userList size : " + pokemons.size());
                    for (Pokemon pokemon : pokemons) {
                        Log.i(TAG, "id : " + pokemon.getID());
                        Log.i(TAG, "name : " + pokemon.getName());
                        Log.i(TAG, "base_experience : " + pokemon.getBase_experience());
                        Log.i(TAG, "height : " + pokemon.getHeight());
                        Log.i(TAG, "weight : " + pokemon.getWeight());
                        Log.i(TAG, "move : " + pokemon.getMove());
                        Log.i(TAG, "ability : " + pokemon.getAbility());

                        name.setText(pokemon.getName());
                        idTV.setText(pokemon.getID());
                        weight.setText(pokemon.getWeight());
                        height.setText(pokemon.getHeight());
                        basexp.setText(pokemon.getBase_experience());
                        move.setText(pokemon.getMove());
                        ability.setText(pokemon.getAbility());




                    }
                }
                @Override
                public void onError(ANError anError) {
                    // handle error
                    Toast.makeText(getApplicationContext(),"Error on getting data ", Toast.LENGTH_LONG).show();
                }
            });
        }



        View.OnClickListener searchListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searched = searchInput.getText().toString();
                boolean valid = true;
                if (searched.contains("%")){
                    valid = false;
                }
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
                        pokemonList = new LinkedList();
                        makeRequest(searched);

                }


                }



        };
        View.OnClickListener listListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
}