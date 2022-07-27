package com.example.recipeopedia.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.recipeopedia.R;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("FavoriteRecipe")
public class FavoriteRecipe extends ParseObject {
    public static final String KEY_RECIPE_NAME = "recipeName";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INSTRUCTIONS = "instructions";
    public static final String KEY_INGREDIENTS = "ingredients";
    public static final String KEY_HEALTH_LABELS = "healthLabels";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_ATTEMPT_PICTURE = "attemptPicture";

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

    public String getHealthLabels() {
        return getString(KEY_HEALTH_LABELS);
    }

    public void setHealthLabels(String healthLabels) {
        put(KEY_HEALTH_LABELS, healthLabels);
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

    public ParseFile getAttemptPicture() { return getParseFile(KEY_ATTEMPT_PICTURE); }

    public String getImageUrl() {
        ParseFile file = getParseFile(KEY_ATTEMPT_PICTURE);
        if (file == null) {
            return null;
        }
        return file.getUrl();
    }

    @BindingAdapter("attemptPicture")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.recipe_image_placeholder)
                .into(view);
    }
}