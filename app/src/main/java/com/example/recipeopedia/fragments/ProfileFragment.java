package com.example.recipeopedia.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipeopedia.R;
import com.example.recipeopedia.RecipeKeys;
import com.example.recipeopedia.activities.EditProfileActivity;
import com.example.recipeopedia.models.Recipe;
import com.parse.Parse;
import com.parse.ParseUser;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private ImageView ivProfileImage;
    private TextView tvUsername, tvEmail, tvPhoneNumber, tvBio;
    private Button btnEditProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivProfileImage = view.findViewById(R.id.ivProfileImage);
        tvUsername = view.findViewById(R.id.tvUsername);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);
        tvBio = view.findViewById(R.id.tvBio);
        ivProfileImage = view.findViewById(R.id.ivProfileImage);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick edit button");
                goEditProfileActivity();
            }
        });

        ParseUser currentUser = ParseUser.getCurrentUser();
        tvUsername.setText(currentUser.getUsername());
        tvEmail.setText(currentUser.getEmail());
        tvPhoneNumber.setText((String) currentUser.get(RecipeKeys.KEY_PHONE_NUMBER));
        tvBio.setText((String) currentUser.get(RecipeKeys.KEY_BIO));
        // TODO: show more info on My Account page (first name, last name, etc.)
    }

    private void goEditProfileActivity() {
        Intent i = new Intent(getContext(), EditProfileActivity.class);
        startActivity(i);
    }
}