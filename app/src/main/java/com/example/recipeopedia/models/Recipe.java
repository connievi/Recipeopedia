package com.example.recipeopedia.models;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.recipeopedia.R;
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
    public String recipeName, image, instructions, ingredients, href, healthLabels;

    public Recipe() {}

    public Recipe(JSONObject jsonObject) throws JSONException {
        JSONObject recipeObject = jsonObject.getJSONObject("recipe");
        recipeName = recipeObject.getString("label");
        image = recipeObject.getString("image");
        instructions = recipeObject.getString("url");
        ingredients = (recipeObject.getJSONArray("ingredientLines")).toString();
        href = jsonObject.getJSONObject("_links").getJSONObject("self").getString("href");
        healthLabels = (recipeObject.getJSONArray("healthLabels")).toString();
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
        return formatIngredients(ingredients);
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

    public String getHealthLabels() { return formatHealthLabels(healthLabels); }

    private String formatIngredients(String ingredients) {
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("\"(.*?)\"", Pattern.CASE_INSENSITIVE);
        ingredients.replaceAll("\\\\", "");
        Matcher matcher = pattern.matcher(ingredients);
        while (matcher.find()) {
            String ingredientLine = matcher.group().replaceAll("^\"|\"$", "");
            ingredientLine = ingredientLine.replaceAll("\\\\", "");
            sb.append(ingredientLine + "\n");
        }
        String ret = sb.toString();
        return ret.trim();
    }

    private String formatHealthLabels(String healthLabels) {
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("\"(.*?)\"", Pattern.CASE_INSENSITIVE);
        healthLabels.replaceAll("\\\\", "");
        Matcher matcher = pattern.matcher(healthLabels);
        while (matcher.find()) {
            String label = matcher.group().replaceAll("^\"|\"$", "");
            label = label.replaceAll("\\\\", "");
            sb.append(label + ", ");
        }
        String ret = sb.toString();
        return ret.substring(0, ret.length() - 2);
    }

    @BindingAdapter("recipeImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.recipe_image_placeholder)
                .into(view);
    }
}