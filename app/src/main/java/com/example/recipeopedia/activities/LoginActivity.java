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
    private EditText etUsername, etPassword;
    private Button btnLogin, btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                logInUser(username, password);
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

    private void logInUser(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (username.isEmpty() && password.isEmpty()) {
                    errorAnimation(R.id.etUsername);
                    errorAnimation(R.id.etPassword);
                }
                else if (username.isEmpty()) {
                    Toast.makeText(LoginActivity.this, R.string.enter_username, Toast.LENGTH_SHORT).show();
                    errorAnimation(R.id.etUsername);
                }
                else if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, R.string.enter_password, Toast.LENGTH_SHORT).show();
                    errorAnimation(R.id.etPassword);
                }
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

    private void errorAnimation(int resourceId) {
        YoYo.with(Techniques.Shake)
                .duration(600)
                .playOn(findViewById(resourceId));
    }
}