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
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    private ImageView etIcon;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        etIcon = findViewById(R.id.etIcon);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (validateInput(username, password)) {
                    logInUser(username, password);
                }
            }
        });

        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpActivity();
            }
        });
    }

    private boolean validateInput(String username, String password) {
        boolean check = true;
        if (username.isEmpty() && password.isEmpty()) {
            Toast.makeText(LoginActivity.this, R.string.enter_user_and_password, Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Shake)
                    .duration(600)
                    .playOn(findViewById(R.id.etUsername));
            YoYo.with(Techniques.Shake)
                    .duration(600)
                    .playOn(findViewById(R.id.etPassword));
            check = false;
        }
        else if (username.isEmpty()) {
            Toast.makeText(LoginActivity.this, R.string.enter_username, Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Shake)
                    .duration(600)
                    .playOn(findViewById(R.id.etUsername));
            check = false;
        }
        else if (password.isEmpty()) {
            Toast.makeText(LoginActivity.this, R.string.enter_password, Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Shake)
                    .duration(600)
                    .playOn(findViewById(R.id.etPassword));
            check = false;
        }
        return check;
    }

    private void logInUser(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Toast.makeText(LoginActivity.this, R.string.incorrect_login_info, Toast.LENGTH_SHORT).show();
                    return;
                }
                goMainActivity();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    private void signUpActivity() {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
        finish();
    }
}