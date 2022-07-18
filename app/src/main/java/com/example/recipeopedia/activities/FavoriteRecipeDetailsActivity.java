package com.example.recipeopedia.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.recipeopedia.R;
import com.example.recipeopedia.databinding.ActivityFavoriteRecipeDetailsBinding;
import com.example.recipeopedia.databinding.ActivityRecipeDetailsBinding;
import com.example.recipeopedia.models.FavoriteRecipe;
import com.example.recipeopedia.models.Recipe;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

public class FavoriteRecipeDetailsActivity extends AppCompatActivity {
    public static final String TAG = "FavoriteRecipeDetailsActivity";
    private FavoriteRecipe favoriteRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipe_details);

        favoriteRecipe = Parcels.unwrap(getIntent().getParcelableExtra(FavoriteRecipe.class.getSimpleName()));
        Log.d("FavoriteRecipeDetailsActivity",
                String.format("Showing details for '%s'", favoriteRecipe.getRecipeName()));

        ActivityFavoriteRecipeDetailsBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_favorite_recipe_details);
        binding.setImageUrl(favoriteRecipe.getImage());
        binding.setFavoriteRecipe(favoriteRecipe);
    }
}