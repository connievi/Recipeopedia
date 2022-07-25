package com.example.recipeopedia.models;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Attempt")
public class Attempt extends ParseObject {
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_ATTEMPT = "attempt";
    public static final String KEY_RECIPE_NAME = "recipeName";

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public String getAttempt() { return getString(KEY_ATTEMPT); }

    public void setAttempt(String attempt) { put(KEY_ATTEMPT, attempt); }

    public String getRecipeName() {
        return getString(KEY_RECIPE_NAME);
    }

    public void setRecipeName(String recipeName) {
        put(KEY_RECIPE_NAME, recipeName);
    }
}
