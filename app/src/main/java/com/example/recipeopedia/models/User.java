package com.example.recipeopedia.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.recipeopedia.R;
import com.example.recipeopedia.RecipeKeys;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class User {
    private String username, password, firstName, lastName, email, phoneNumber, bio, imageUrl;
    ParseFile profilePicture;

    public User() {}

    public User(ParseUser user) {
        username = user.getUsername();
        password = (String) user.get(RecipeKeys.KEY_PASSWORD);
        firstName = (String) user.get(RecipeKeys.KEY_FIRST_NAME);
        lastName = (String) user.get(RecipeKeys.KEY_LAST_NAME);
        email = user.getEmail();
        phoneNumber = (String) user.get(RecipeKeys.KEY_PHONE_NUMBER);
        bio = (String) user.get(RecipeKeys.KEY_BIO);
        profilePicture = (ParseFile) user.get(RecipeKeys.KEY_PROFILE_PICTURE);
        imageUrl = profilePicture.getUrl();
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

    public ParseFile getProfilePicture() { return profilePicture; }

    public String getImageUrl() { return imageUrl; }

    @BindingAdapter("profilePicture")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.profile_image_placeholder)
                .into(view);
    }
}
