package com.example.recipeopedia.models;

import com.example.recipeopedia.RecipeKeys;
import com.parse.ParseUser;

public class User {
    private String username, password, firstName, lastName, email, phoneNumber, bio;

    public User() {}

    public User(ParseUser user) {
        username = user.getUsername();
        password = (String) user.get(RecipeKeys.KEY_PASSWORD);
        firstName = (String) user.get(RecipeKeys.KEY_FIRST_NAME);
        lastName = (String) user.get(RecipeKeys.KEY_LAST_NAME);
        email = user.getEmail();
        phoneNumber = (String) user.get(RecipeKeys.KEY_PHONE_NUMBER);
        bio = (String) user.get(RecipeKeys.KEY_BIO);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBio() {
        return bio;
    }

    public String getFullName() { return firstName + " " + lastName; }
}
