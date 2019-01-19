package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    //views

    private TextView itemName,itemAuxiliaryNames,placeOfOrigin,description,ingredients;
    private ImageView itemImage;
    private RelativeLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        itemImage = findViewById(R.id.image_iv);
        itemName = findViewById(R.id.origin_name);
        itemAuxiliaryNames = findViewById(R.id.other_name);
        placeOfOrigin = findViewById(R.id.place);
        description = findViewById(R.id.content);
        ingredients = findViewById(R.id.ingredient);

        progressBar = findViewById(R.id.loading);



        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        setTitle(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich data) {

        Picasso.with(DetailActivity.this).load(data.getImage()).into(itemImage);
        itemName.setText(data.getMainName());
        String n = "";
        for(String x:data.getAlsoKnownAs())
            n+=x+" ";
        itemAuxiliaryNames.setText(n.trim());
        n="";
        for(String x:data.getIngredients())
            n+=x+" ";
        ingredients.setText(n.trim());
        placeOfOrigin.setText(data.getPlaceOfOrigin());
        description.setText(data.getDescription());


    }

}
