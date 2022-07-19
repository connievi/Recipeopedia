package com.example.recipeopedia.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.recipeopedia.R;
import com.example.recipeopedia.databinding.ActivityRecipeDetailsBinding;
import com.example.recipeopedia.models.FavoriteRecipe;
import com.example.recipeopedia.models.Recipe;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

public class RecipeDetailsActivity extends AppCompatActivity {
    public static final String TAG = "RecipeDetailsActivity";
    private Recipe recipe;
    private Button btnSave, btnReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        recipe = Parcels.unwrap(getIntent().getParcelableExtra(Recipe.class.getSimpleName()));
        Log.d("RecipeDetailsActivity", String.format("Showing details for '%s'", recipe.getRecipeName()));

        ActivityRecipeDetailsBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_recipe_details);
        binding.setImageUrl(recipe.getImage());
        binding.setRecipe(recipe);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick save recipe button");
                try {
                    saveRecipe(recipe.getRecipeName(), ParseUser.getCurrentUser(), recipe.getImage(),
                            recipe.getIngredients(), recipe.getInstructions());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        btnReview = findViewById(R.id.btnReview);
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick view recipe reviews button");
                Intent i = new Intent(getApplicationContext(), ReviewSectionActivity.class);
                i.putExtra(Recipe.class.getSimpleName(), Parcels.wrap(recipe));
                startActivity(i);
                finish();
            }
        });
    }

    private void saveRecipe(String recipeName, ParseUser currentUser, String imageUrl,
                                  String ingredients, String instructions) throws ParseException {
        ParseQuery<FavoriteRecipe> query = ParseQuery.getQuery(FavoriteRecipe.class);
        query.whereEqualTo(FavoriteRecipe.KEY_RECIPE_NAME, recipeName);
        if (query.count() > 0) {
            Toast.makeText(getApplicationContext(), "Recipe already saved!", Toast.LENGTH_SHORT).show();
        }
        else {
            FavoriteRecipe favoriteRecipe = new FavoriteRecipe();
            favoriteRecipe.setRecipeName(recipeName);
            favoriteRecipe.setImage(imageUrl);
            favoriteRecipe.setUser(currentUser);
            favoriteRecipe.setIngredients(ingredients);
            favoriteRecipe.setInstructions(instructions);
            favoriteRecipe.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Log.e(TAG, "Error while saving", e);
                        Toast.makeText(getApplicationContext(), "Error while saving!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Log.i(TAG, "Favorite recipe saved successfully");
                        Toast.makeText(getApplicationContext(), "Recipe saved!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}