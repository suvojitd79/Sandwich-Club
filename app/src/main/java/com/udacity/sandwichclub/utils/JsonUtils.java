package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    //IMPROVEMENTs

    public static final String KEY_NAME = "name";
    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_ALSO_KNOW_AS = "alsoKnownAs";
    public static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INGREDIENTS = "ingredients";



    public static Sandwich parseSandwichJson(String json) {


        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject name = jsonObject.getJSONObject(KEY_NAME);
            String mainName = name.getString(KEY_MAIN_NAME);
            JSONArray Names = name.getJSONArray(KEY_ALSO_KNOW_AS);
            ArrayList<String> subNames = new ArrayList<>();

            if(Names!=null){
                for(int i=0;i<Names.length();i++)
                {
                    subNames.add(Names.getString(i));
                }}

            String placeOfOrigin = jsonObject.getString(KEY_PLACE_OF_ORIGIN);
            String description = jsonObject.getString(KEY_DESCRIPTION);
            String image = jsonObject.getString(KEY_IMAGE);

            JSONArray ingredient = jsonObject.getJSONArray(KEY_INGREDIENTS);
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
