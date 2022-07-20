package com.example.recipeopedia.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
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
import com.example.recipeopedia.databinding.ActivityRecipeDetailsBinding;
import com.example.recipeopedia.databinding.FragmentProfileBinding;
import com.example.recipeopedia.models.Recipe;
import com.example.recipeopedia.models.User;
import com.parse.Parse;
import com.parse.ParseUser;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private ImageView ivProfileImage;
    private Button btnEditProfile;
    private User user;
    private FragmentProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO: show more info on My Account page (profile image, first name, last name, etc.)
        // TODO: bind profile image
        ivProfileImage = view.findViewById(R.id.ivProfileImage);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goEditProfileActivity();
            }
        });

        ParseUser currentUser = ParseUser.getCurrentUser();
        user = new User(currentUser);
        binding.setUser(user);
    }

    private void goEditProfileActivity() {
        Intent i = new Intent(getContext(), EditProfileActivity.class);
        startActivity(i);
    }
}