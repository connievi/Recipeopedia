package com.example.recipeopedia.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String recipeName;
    private String ingredients;
    private String nutrition;
    private String instructions;
    private String mealType;
    private String cuisineType;
    private String  dishType;
    private String dietLabels;
    private String healthLabels;
    private String image;

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
