package com.example.recipeopedia.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.recipeopedia.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    public static final String TAG = "SignUpActivity";
    private EditText etEmail;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnCreate;
    private ImageView etIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etIcon = findViewById(R.id.etIcon);
        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                if (username.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Username cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String password = etPassword.getText().toString();
                if (password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = etEmail.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Email cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    saveUser(email, username, password);
                    Toast.makeText(SignUpActivity.this, "Account successfully made!", Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void saveUser(String email, String username, String password) throws ParseException {
        ParseUser newUser = new ParseUser();
        newUser.put("username", username);
        newUser.put("password", password);
        newUser.put("email", email);
        newUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(SignUpActivity.this, "Issue with creating account!", Toast.LENGTH_SHORT).show();
                    return;
                }
                goEditProfileActivity();
            }
        });
    }

    private void goEditProfileActivity() {
        Intent i = new Intent(this, EditProfileActivity.class);
        startActivity(i);
        finish();
    }
}