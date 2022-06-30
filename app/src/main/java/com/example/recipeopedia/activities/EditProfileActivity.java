package com.example.recipeopedia.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recipeopedia.R;
import com.parse.ParseUser;

public class EditProfileActivity extends AppCompatActivity {
    public static final String TAG = "EditProfileActivity";
    private EditText etFirstName, etLastName, etEmail, etPhoneNumber, etBio;
    private Button btnUpdate;

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
                validateInput();
                editProfile(v);
                goMainActivity();
            }
        });
    }

    private boolean validateInput() {
        if (etFirstName.getText().toString().isEmpty()) {
            etFirstName.setError("Please Enter First Name");
            return false;
        }
        if (etLastName.getText().toString().isEmpty()) {
            etLastName.setError("Please Enter Last Name");
            return false;
        }
        if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("Please Enter Email");
            return false;
        }
        if (etPhoneNumber.getText().toString().isEmpty()) {
            etPhoneNumber.setError("Please Enter Contact No");
            return false;
        }
        if (etBio.getText().toString().isEmpty()) {
            etBio.setError("Please Enter Designation ");
            return false;
        }

        if (!isEmailValid(etEmail.getText().toString())) {
            etEmail.setError("Please Enter Valid Email");
            return false;
        }
        return true;
    }

    boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void editProfile(View v) {
        if (validateInput()) {
            String firstName = etFirstName.getText().toString();
            String lastName = etLastName.getText().toString();
            String email = etEmail.getText().toString();
            String phoneNumber = etPhoneNumber.getText().toString();
            String bio = etBio.getText().toString();


            // WORK ON SAVING THE UPDATED INFO
            ParseUser currentUser = ParseUser.getCurrentUser();
            currentUser.put("firstName", firstName);
            currentUser.put("lastName", lastName);
            currentUser.put("phoneNumber", phoneNumber);
            currentUser.put("bio", bio);
            currentUser.put("email", email);
            currentUser.saveInBackground();
            Toast.makeText(this,"Profile Update Successfully",Toast.LENGTH_SHORT).show();
        }
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}