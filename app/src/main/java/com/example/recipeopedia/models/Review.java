package com.example.recipeopedia.models;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@ParseClassName("Review")
public class Review extends ParseObject {
    public static final String KEY_USER = "user";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_RECIPE_ID = "recipeId";
    public static final String KEY_REVIEW = "review";

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public String getUsername() {
        return getString(KEY_USERNAME);
    }

    public void setUsername(String username) {
        put(KEY_USERNAME, username);
    }

    public String getRecipeId() {
        return getString(KEY_RECIPE_ID);
    }

    public void setRecipeId(String recipeId) {
        put(KEY_RECIPE_ID, recipeId);
    }

    public String getReview() {
        return getString(KEY_REVIEW);
    }

    public void setReview(String review) {
        put(KEY_REVIEW, review);
    }
}