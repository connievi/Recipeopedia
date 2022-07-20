package com.example.recipeopedia.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("FavoriteRecipe")
public class FavoriteRecipe extends ParseObject {
    public static final String KEY_RECIPE_NAME = "recipeName";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INSTRUCTIONS = "instructions";
    public static final String KEY_INGREDIENTS = "ingredients";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED = "createdAt";

    public String getRecipeName() {
        return getString(KEY_RECIPE_NAME);
    }

    public void setRecipeName(String recipeName) {
        put(KEY_RECIPE_NAME, recipeName);
    }

    public String getIngredients() {
        return getString(KEY_INGREDIENTS);
    }

    public void setIngredients(String ingredients) {
        put(KEY_INGREDIENTS, ingredients);
    }

    public String getInstructions() {
        return getString(KEY_INSTRUCTIONS);
    }

    public void setInstructions(String instructions) {
        put(KEY_INSTRUCTIONS, instructions);
    }

    public String getImage() {
        return getString(KEY_IMAGE);
    }

    public void setImage(String imageUrl) {
        put(KEY_IMAGE, imageUrl);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }
}