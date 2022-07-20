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

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
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
                String email = etEmail.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (validateInput(email, username, password)) {
                    try {
                        saveUser(email, username, password);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private boolean validateInput(String email, String username, String password) {
        boolean check = true;
        if (email.isEmpty()) {
            YoYo.with(Techniques.Shake)
                    .duration(600)
                    .playOn(findViewById(R.id.etEmail));
            check = false;
        }
        if (username.isEmpty()) {
            YoYo.with(Techniques.Shake)
                    .duration(600)
                    .playOn(findViewById(R.id.etUsername));
            check = false;
        }
        if (password.isEmpty()) {
            YoYo.with(Techniques.Shake)
                    .duration(600)
                    .playOn(findViewById(R.id.etPassword));
            check = false;
        }
        if (!check) {
            Toast.makeText(SignUpActivity.this, R.string.enter_account_info, Toast.LENGTH_SHORT).show();
        }
        return check;
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
                    Toast.makeText(SignUpActivity.this, R.string.issue_creating_account, Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Toast.makeText(SignUpActivity.this, R.string.account_made, Toast.LENGTH_SHORT).show();
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