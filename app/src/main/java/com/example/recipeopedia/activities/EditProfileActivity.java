package com.example.recipeopedia.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.recipeopedia.R;
import com.example.recipeopedia.RecipeKeys;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.File;

public class EditProfileActivity extends AppCompatActivity {
    public static final String TAG = "EditProfileActivity";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    private EditText etFirstName, etLastName, etEmail, etPhoneNumber, etBio;
    private Button btnEditProfile, btnCaptureImage;
    private String firstName, lastName, email, phoneNumber, bio;
    private ImageView ivProfileImage;
    public String photoFileName = "photo.jpg";
    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ivProfileImage = findViewById(R.id.ivProfileImage);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etBio = findViewById(R.id.etBio);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile(v);
            }
        });

        btnCaptureImage = findViewById(R.id.btnCaptureImage);
        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
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
            errorAnimation(R.id.etFirstName);
        }
        if (lastName.isEmpty()) {
            etLastName.setError(getString(R.string.enter_last_name));
            check = false;
            errorAnimation(R.id.etLastName);
        }
        if (email.isEmpty()) {
            etEmail.setError(getString(R.string.enter_email));
            check = false;
            errorAnimation(R.id.etEmail);
        }
        if (phoneNumber.isEmpty()) {
            etPhoneNumber.setError(getString(R.string.enter_phone_number));
            check = false;
            errorAnimation(R.id.etPhoneNumber);
        }
        if (bio.isEmpty()) {
            etBio.setError(getString(R.string.enter_bio));
            check = false;
            errorAnimation(R.id.etBio);
        }
        if (!isEmailValid(email)) {
            etEmail.setError(getString(R.string.enter_valid_email));
            check = false;
            errorAnimation(R.id.etEmail);
        }
        return check;
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void errorAnimation(int resourceId) {
        YoYo.with(Techniques.Shake)
                .duration(600)
                .playOn(findViewById(resourceId));
    }

    public void editProfile(View v) {
        if (validateInput()) {
            ParseUser currentUser = ParseUser.getCurrentUser();
            currentUser.put(RecipeKeys.KEY_FIRST_NAME, firstName);
            currentUser.put(RecipeKeys.KEY_LAST_NAME, lastName);
            currentUser.put(RecipeKeys.KEY_PHONE_NUMBER, phoneNumber);
            currentUser.put(RecipeKeys.KEY_BIO, bio);
            currentUser.put(RecipeKeys.KEY_EMAIL, email);
            if (photoFile != null) {
                currentUser.put(RecipeKeys.KEY_PROFILE_PICTURE, new ParseFile(photoFile));
            }
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

    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri(photoFileName);
        Uri fileProvider = FileProvider.getUriForFile(this, "com.codepath.fileprovider.connie", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                ivProfileImage.setImageBitmap(takenImage);
            } else {
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File getPhotoFileUri(String photoFileName) {
        File mediaStorageDir = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }
        return new File(mediaStorageDir.getPath() + File.separator + photoFileName);
    }
}