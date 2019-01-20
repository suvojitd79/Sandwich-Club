package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.HashMap;


public class DetailActivity extends AppCompatActivity {

    //store the country name - flag id
    private HashMap<String,Integer> country = new HashMap<>();

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    //views

    private TextView itemName,itemAuxiliaryNames,description,ingredients,origin;
    private ImageView itemImage;
    private ImageView country_img;
    private RelativeLayout country_section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        itemImage = findViewById(R.id.image_iv);
        itemAuxiliaryNames = findViewById(R.id.other_name);
        description = findViewById(R.id.content);
        ingredients = findViewById(R.id.ingredient);
        origin = findViewById(R.id.origin);
        country_img = findViewById(R.id.country_image);
        country_section = findViewById(R.id.country_zone);

        country.put("Austria",R.drawable.austria);
        country.put("Uruguay",R.drawable.uruguay);
        country.put("United States",R.drawable.united_states);
        country.put("Taiwan",R.drawable.taiwan);
        country.put("Cuba",R.drawable.cuba);
        country.put("Serbia",R.drawable.serbia);
        country.put("China",R.drawable.china);
        country.put("Middle East",R.drawable.me2);
        country.put("India",R.drawable.india);


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
        String n = "";
        int x1 = data.getAlsoKnownAs().size();
        if(x1>2)
            x1 = 2;
        if(x1==0)
            itemAuxiliaryNames.setVisibility(View.GONE);
        else {
            int i=0;
            for (i = 0; i < x1 - 1; i++)
                n += data.getAlsoKnownAs().get(i) + " ,";
            n += data.getAlsoKnownAs().get(i);
            itemAuxiliaryNames.setText(n.trim());
        }

        n="";
        x1 = data.getIngredients().size();
        if(x1==0)
            ingredients.setVisibility(View.GONE);
        else {
            int i=0;
            for (i = 0; i < x1 - 1; i++)
                n += data.getIngredients().get(i) + ", ";
            n += data.getIngredients().get(i);
            ingredients.setText(n.trim());
        }



        if(country.get(data.getPlaceOfOrigin())!=null) {
            origin.setText(" "+data.getPlaceOfOrigin());
            country_img.setImageResource(country.get(data.getPlaceOfOrigin()));
        }else{

            country_section.setVisibility(View.GONE);
        }

        description.setText(data.getDescription());


    }

}
