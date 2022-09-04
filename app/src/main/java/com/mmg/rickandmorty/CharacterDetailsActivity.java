package com.mmg.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mmg.rickandmorty.databinding.ActivityCharacterDetailsBinding;
import com.mmg.rickandmorty.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

public class CharacterDetailsActivity extends AppCompatActivity {

    private ActivityCharacterDetailsBinding characterDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        characterDetailsBinding = ActivityCharacterDetailsBinding.inflate(getLayoutInflater());
        setContentView(characterDetailsBinding.getRoot());

        int id = getIntent().getIntExtra("Movie Id", 0);
        String name = getIntent().getStringExtra("Character Name");
        String status = getIntent().getStringExtra("Character Status");
        String locationName = getIntent().getStringExtra("Character Location");
        String characterImage = getIntent().getStringExtra("Character Image");

        characterDetailsBinding.textViewName.setText( name);
        characterDetailsBinding.textViewStatus.setText( "Status: " + status);
        characterDetailsBinding.textViewLocationName.setText( "Location: " + locationName);
        Picasso.with(getApplicationContext()).load(characterImage).into(characterDetailsBinding.imageView);

        characterDetailsBinding.textViewBackToSearch.setOnClickListener(view -> {  onBackPressed(); });
        characterDetailsBinding.imageViewBackToSearch.setOnClickListener(view -> { onBackPressed();  });

    }
}