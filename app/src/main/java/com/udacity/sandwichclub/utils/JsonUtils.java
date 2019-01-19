package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {


        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject name = jsonObject.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray Names = name.getJSONArray("alsoKnownAs");
            ArrayList<String> subNames = new ArrayList<>();

            if(Names!=null){
                for(int i=0;i<Names.length();i++)
                {
                    subNames.add(Names.getString(i));
                }}

            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            String description = jsonObject.getString("description");
            String image = jsonObject.getString("image");

            JSONArray ingredient = jsonObject.getJSONArray("ingredients");
            ArrayList<String> ingredients = new ArrayList<>();

            if(ingredient != null){
                for(int i=0;i<ingredient.length();i++)
                {
                    ingredients.add(ingredient.getString(i));
                }}
            Sandwich sandwich = new Sandwich(mainName,subNames,placeOfOrigin,description,image,ingredients);

            Log.d("fudge",ingredients.toString());


            return sandwich;

        } catch (JSONException e) {
                e.printStackTrace();
        }

        return new Sandwich();
    }
}
