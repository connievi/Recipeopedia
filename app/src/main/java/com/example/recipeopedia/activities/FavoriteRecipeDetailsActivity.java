package com.example.recipeopedia.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recipeopedia.R;
import com.example.recipeopedia.databinding.ActivityFavoriteRecipeDetailsBinding;
import com.example.recipeopedia.models.FavoriteRecipe;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

public class FavoriteRecipeDetailsActivity extends AppCompatActivity {
    public static final String TAG = "FavoriteRecipeDetailsActivity";
    private FavoriteRecipe favoriteRecipe;
    private Button btnPostAttempt;
    private EditText etYourAttempt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipe_details);

        favoriteRecipe = Parcels.unwrap(getIntent().getParcelableExtra(FavoriteRecipe.class.getSimpleName()));
        ActivityFavoriteRecipeDetailsBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_favorite_recipe_details);
        binding.setImageUrl(favoriteRecipe.getImage());
        binding.setFavoriteRecipe(favoriteRecipe);

        etYourAttempt = findViewById(R.id.etYourAttempt);
        btnPostAttempt = findViewById(R.id.btnPostAttempt);
        ParseUser currentUser = ParseUser.getCurrentUser();
        String description = etYourAttempt.getText().toString();
        btnPostAttempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (description.isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.description_empty, Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    try {
                        saveAttempt(favoriteRecipe, currentUser, description);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void saveAttempt(FavoriteRecipe favoriteRecipe, ParseUser currentUser, String description) throws ParseException {
        favoriteRecipe.setAttempt(description);
        favoriteRecipe.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    return;
                }
            }
        });
    }
}