package com.example.recipeopedia.models;

import com.parse.ParseClassName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Recipe {
    String recipeName;
    String ingredients;
    String nutrition;
    String instructions;
    String mealType;
    String cuisineType;
    String dishType;
    String dietLabels;
    String healthLabels;
    String image;

    public Recipe() {}

    public Recipe(JSONObject jsonObject) throws JSONException {
        recipeName = jsonObject.getJSONObject("recipe").getString("label");
        ingredients = (jsonObject.getJSONObject("recipe").getJSONArray("ingredientLines")).toString();
        dietLabels = (jsonObject.getJSONObject("recipe").getJSONArray("dietLabels")).toString();
        healthLabels = (jsonObject.getJSONObject("recipe").getJSONArray("healthLabels")).toString();
        mealType = (jsonObject.getJSONObject("recipe").getJSONArray("mealType")).toString();
        dishType = jsonObject.getJSONObject("recipe").getString("dishType");
        cuisineType = jsonObject.getJSONObject("recipe").getString("dishType");
        instructions = jsonObject.getJSONObject("recipe").getString("url");
        image = jsonObject.getJSONObject("recipe").getString("image");
        // nutrition = (jsonObject.getJSONObject("recipe").getJSONArray("totalNutrients")).toString();
    }

    public static List<Recipe> fromJsonArray(JSONArray recipeJsonArray) throws JSONException {
        List<Recipe> recipes = new ArrayList<>();
        for (int i = 0; i < recipeJsonArray.length(); i++)
        {
            recipes.add(new Recipe(recipeJsonArray.getJSONObject(i)));
        }
        return recipes;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getImage() {
        return image;
    }
}