package com.example.recipeopedia.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.recipeopedia.R;
import com.example.recipeopedia.RecipeKeys;
import com.parse.ParseUser;

public class EditProfileActivity extends AppCompatActivity {
    public static final String TAG = "EditProfileActivity";
    private EditText etFirstName, etLastName, etEmail, etPhoneNumber, etBio;
    private Button btnUpdate;

    String firstName, lastName, email, phoneNumber, bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etBio = findViewById(R.id.etBio);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile(v);
            }
        });
    }

    private boolean validateInput() {
        firstName = etFirstName.getText().toString();
        lastName = etLastName.getText().toString();
        email = etEmail.getText().toString();
        phoneNumber = etPhoneNumber.getText().toString();
        bio = etBio.getText().toString();

        boolean check = true;
        if (firstName.isEmpty()) {
            etFirstName.setError(getString(R.string.enter_first_name));
            check = false;
            YoYo.with(Techniques.Shake)
                    .duration(600)
                    .playOn(findViewById(R.id.layout_first_name));
        }
        if (lastName.isEmpty()) {
            etLastName.setError(getString(R.string.enter_last_name));
            check = false;
            YoYo.with(Techniques.Shake)
                    .duration(600)
                    .playOn(findViewById(R.id.layout_last_name));
        }
        if (email.isEmpty()) {
            etEmail.setError(getString(R.string.enter_email));
            check = false;
            YoYo.with(Techniques.Shake)
                    .duration(600)
                    .playOn(findViewById(R.id.layout_email));
        }
        if (phoneNumber.isEmpty()) {
            etPhoneNumber.setError(getString(R.string.enter_phone_number));
            check = false;
            YoYo.with(Techniques.Shake)
                    .duration(600)
                    .playOn(findViewById(R.id.layout_phone_number));
        }
        if (bio.isEmpty()) {
            etBio.setError(getString(R.string.enter_bio));
            check = false;
            YoYo.with(Techniques.Shake)
                    .duration(600)
                    .playOn(findViewById(R.id.layout_bio));
        }
        if (!isEmailValid(email)) {
            etEmail.setError(getString(R.string.enter_valid_email));
            check = false;
            YoYo.with(Techniques.Shake)
                    .duration(600)
                    .playOn(findViewById(R.id.layout_email));
        }
        return check;
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void editProfile(View v) {
        if (validateInput()) {
            ParseUser currentUser = ParseUser.getCurrentUser();
            currentUser.put(RecipeKeys.KEY_FIRST_NAME, firstName);
            currentUser.put(RecipeKeys.KEY_LAST_NAME, lastName);
            currentUser.put(RecipeKeys.KEY_PHONE_NUMBER, phoneNumber);
            currentUser.put(RecipeKeys.KEY_BIO, bio);
            currentUser.put(RecipeKeys.KEY_EMAIL, email);
            currentUser.saveInBackground();
            Toast.makeText(this, R.string.profile_update_success, Toast.LENGTH_SHORT).show();
            goMainActivity();
        }
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}