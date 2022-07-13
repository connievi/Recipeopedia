package com.example.recipeopedia.models;

import android.text.TextUtils;

import com.parse.ParseClassName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Recipe {
    public String recipeName, ingredients, nutrition, instructions,
            mealType, cuisineType, dishType, dietLabels, healthLabels, image;

    public Recipe() {}

    public Recipe(JSONObject jsonObject) throws JSONException {
        JSONObject recipeObject = jsonObject.getJSONObject("recipe");
        recipeName = recipeObject.getString("label");
        ingredients = (recipeObject.getJSONArray("ingredientLines")).toString();
        dietLabels = (recipeObject.getJSONArray("dietLabels")).toString();
        healthLabels = (recipeObject.getJSONArray("healthLabels")).toString();
        mealType = (recipeObject.getJSONArray("mealType")).toString();
        dishType = recipeObject.getString("dishType");
        cuisineType = recipeObject.getString("dishType");
        instructions = recipeObject.getString("url");
        image = recipeObject.getString("image");
        // nutrition = (recipeObject.getJSONArray("totalNutrients")).toString();
    }

    public static List<Recipe> fromJsonArray(JSONArray recipeJsonArray) throws JSONException {
        List<Recipe> recipes = new ArrayList<>();
        for (int i = 0; i < recipeJsonArray.length(); i++)
        {
            recipes.add(new Recipe(recipeJsonArray.getJSONObject(i)));
        }
        return recipes;
    }

    public static Recipe fromJson(JSONObject jsonObject) {
        Recipe recipe = new Recipe();
        try {
            recipe.recipeName = jsonObject.getString("label");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return recipe;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getIngredients() {
        return formatString(ingredients);
    }

    public String getInstructions() {
        return instructions;
    }

    public String getImage() {
        return image;
    }

    private String formatString(String text) {
        /*
        formatString() formats the nested JSON data that ingredients, instructions,
        and other values are stored in
        TODO: this method does not completely format the string how I want, will edit later
        */

        StringBuilder json = new StringBuilder();
        String indentString = "";

        boolean inQuotes = false;
        boolean isEscaped = false;

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);

            switch (letter) {
                case '\\':
                    isEscaped = !isEscaped;
                    break;
                case '"':
                    if (!isEscaped) {
                        inQuotes = !inQuotes;
                    }
                    break;
                default:
                    isEscaped = false;
                    break;
            }

            if (!inQuotes && !isEscaped) {
                switch (letter) {
                    case '{':
                    case '[':
                        json.append("\n" + indentString + letter + "\n");
                        indentString = indentString + "\t";
                        json.append(indentString);
                        break;
                    case '}':
                    case ']':
                        indentString = indentString.replaceFirst("\t", "");
                        json.append("\n" + indentString + letter);
                        break;
                    case ',':
                        json.append(letter + "\n" + indentString);
                        break;
                    default:
                        json.append(letter);
                        break;
                }
            } else {
                json.append(letter);
            }
        }
        return json.toString();
    }
}