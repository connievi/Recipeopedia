package com.example.recipeopedia.models;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Parcel
public class Recipe {
    public String recipeName, ingredients, nutrition, instructions, mealType, cuisineType,
            dishType, dietLabels, healthLabels, image, href;

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
        href = jsonObject.getJSONObject("_links").getJSONObject("self").getString("href");
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
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getImage() {
        return image;
    }

    public String getExternalId() {
        Pattern pattern = Pattern.compile("[^\\/][\\w]+(?=\\?)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(href);
        while (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    @BindingAdapter("recipeImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }

    /*private String formatString(String text) {
        *//*
        formatString() formats the nested JSON data that ingredients, instructions,
        and other values are stored in
        TODO: this method does not completely format the string how I want, will edit later
        *//*

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
    }*/
}