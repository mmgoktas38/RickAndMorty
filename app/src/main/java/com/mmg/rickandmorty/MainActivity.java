package com.mmg.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mmg.rickandmorty.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private List<Characters> charactersList = new ArrayList<>();
    private List<Characters> charactersListForSearch = new ArrayList<>();
    private Adapter adapter;
    private final String api = "https://rickandmortyapi.com/api/character";
    private final String searchCharacterApi = "https://rickandmortyapi.com/api/character/?name=";
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        dialog = new Dialog(this);
        charactersList.clear();
        buildRecyclerView();

        getAllCharacters(api);

        mainBinding.textViewCancel.setOnClickListener(view -> {
            mainBinding.editTextSearchCharacter.getText().clear();
            hideKeyboard(this);
        });
        mainBinding.imageViewCancel.setOnClickListener(view -> {
            mainBinding.editTextSearchCharacter.getText().clear();
        });
        mainBinding.editTextSearchCharacter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                charactersListForSearch.clear();
                searchCharacter(editable.toString());
            }
        });

    }

    private void buildRecyclerView(){
        mainBinding.recyclerViewCharacters.setHasFixedSize(true);
        mainBinding.recyclerViewCharacters.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
    }

    public void getAllCharacters(String apiURL){

        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      //  dialog.show();
        //charactersList.clear();
        String characterURL = api + "?page=1";
        // characterURL = https://rickandmortyapi.com/api/character?page=1
        //Log.e("URL", apiURL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                Log.e("response", response);

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String next = info.getString("next");
                    int pages = info.getInt("pages");
                    Log.e("next",next);

                    JSONArray jsonArrayResults = jsonObject.getJSONArray("results");

                    for (int i = 0; i <jsonArrayResults.length(); i++){

                        JSONObject jsonObjectCharacter = jsonArrayResults.getJSONObject(i);
                        int id = jsonObjectCharacter.getInt("id");
                        String name = jsonObjectCharacter.getString("name");
                        String status = jsonObjectCharacter.getString("status");
                        String image = jsonObjectCharacter.getString("image");
                        JSONObject location = jsonObjectCharacter.getJSONObject("location");
                        String locationName = location.getString("name");

                        Characters c = new Characters(id,name,status,locationName,image);

                        charactersList.add(c);
                        adapter = new Adapter(MainActivity.this, charactersList);
                        mainBinding.recyclerViewCharacters.setAdapter(adapter);

                    }
                    Log.e("pages",String.valueOf(pages));

                    if (!next.equals("null")){
                        Log.e("girdi","girdi");
                        Log.e("next2",next);
                        getAllCharacters(info.getString("next"));
                    }
                    else {
                      //  dialog.dismiss();
                    }


                } catch (JSONException e ) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    public void searchCharacter(String text){

        String searchCharacterURL = null;
        if (text.contains("https")){
            searchCharacterURL = text;
        }
        else{
            searchCharacterURL = searchCharacterApi + text;
        }
        // searchMovieURL = https://api.themoviedb.org/3/search/movie?api_key=4186844cb1e227ca51b707e60d7238fe&query=doctor

        if (mainBinding.editTextSearchCharacter.getText().toString().equals("")){
            charactersList.clear();
            getAllCharacters(api);
        }
        else {
            // charactersListForSearch.clear();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, searchCharacterURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("response", response);

                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject info = jsonObject.getJSONObject("info");
                        String next = info.getString("next");
                        int pages = info.getInt("pages");
                        Log.e("next",next);

                        JSONArray jsonArrayResults = jsonObject.getJSONArray("results");

                        for (int i = 0; i <jsonArrayResults.length(); i++){

                            JSONObject jsonObjectCharacter = jsonArrayResults.getJSONObject(i);
                            int id = jsonObjectCharacter.getInt("id");
                            String name = jsonObjectCharacter.getString("name");
                            String status = jsonObjectCharacter.getString("status");
                            String image = jsonObjectCharacter.getString("image");
                            JSONObject location = jsonObjectCharacter.getJSONObject("location");
                            String locationName = location.getString("name");

                            Characters c = new Characters(id,name,status,locationName,image);

                            charactersListForSearch.add(c);
                            adapter = new Adapter(MainActivity.this, charactersListForSearch);
                            mainBinding.recyclerViewCharacters.setAdapter(adapter);

                        }
                        Log.e("pages",String.valueOf(pages));

                        if (!next.equals("null")){
                            searchCharacter(next);
                        }

                    } catch (JSONException e ) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error: ", error.toString());
                    error.printStackTrace();
                }
            });

            Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}