package com.example.recipeopedia.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.recipeopedia.R;
import com.example.recipeopedia.adapters.AttemptAdapter;
import com.example.recipeopedia.adapters.ReviewAdapter;
import com.example.recipeopedia.databinding.ActivityFavoriteRecipeDetailsBinding;
import com.example.recipeopedia.models.Attempt;
import com.example.recipeopedia.models.FavoriteRecipe;
import com.example.recipeopedia.models.Recipe;
import com.example.recipeopedia.models.Review;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FavoriteRecipeDetailsActivity extends AppCompatActivity {
    public static final String TAG = "FavoriteRecipeDetailsActivity";
    public static final String KEY_ATTEMPT_PICTURE = "attemptPicture";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    private FavoriteRecipe favoriteRecipe;
    private Button btnViewAttempts, btnCaptureImage, btnUploadImage;
    private ImageView ivRecipeImage;
    public String photoFileName = "attempt_photo.jpg";
    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipe_details);

        favoriteRecipe = Parcels.unwrap(getIntent().getParcelableExtra(FavoriteRecipe.class.getSimpleName()));
        ActivityFavoriteRecipeDetailsBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_favorite_recipe_details);
        binding.setAttemptPicture(favoriteRecipe.getImageUrl());
        binding.setFavoriteRecipe(favoriteRecipe);

        ivRecipeImage = findViewById(R.id.ivRecipeImage);
        btnViewAttempts = findViewById(R.id.btnViewAttempts);
        btnViewAttempts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewAttemptsActivity.class);
                intent.putExtra(FavoriteRecipe.class.getSimpleName(), Parcels.wrap(favoriteRecipe));
                v.getContext().startActivity(intent);
            }
        });

        btnCaptureImage = findViewById(R.id.btnCaptureImage);
        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
            }
        });

        btnUploadImage = findViewById(R.id.btnUploadImage);
        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteRecipe.put(KEY_ATTEMPT_PICTURE, new ParseFile(photoFile));
                favoriteRecipe.saveInBackground();
                Toast.makeText(FavoriteRecipeDetailsActivity.this, R.string.picture_uploaded, Toast.LENGTH_SHORT).show();
                goMainActivity();
                // TODO: navigate to FavoriteRecipesFragment ?
            }
        });
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
                ivRecipeImage.setImageBitmap(takenImage);
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

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}