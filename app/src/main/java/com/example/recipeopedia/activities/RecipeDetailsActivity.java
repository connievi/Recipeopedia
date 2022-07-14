package com.example.recipeopedia.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recipeopedia.R;
import com.example.recipeopedia.databinding.ActivityRecipeDetailsBinding;
import com.example.recipeopedia.models.Recipe;

import org.parceler.Parcels;

public class RecipeDetailsActivity extends AppCompatActivity {
    public static final String TAG = "RecipeDetailsActivity";
    private Recipe recipe;
    private TextView tvRecipeName, tvIngredients, tvInstructions;
    private ImageView ivRecipeImage;

    // TextView nutrition, mealType, cuisineType, dishType, dietLabels, healthLabels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        recipe = Parcels.unwrap(getIntent().getParcelableExtra(Recipe.class.getSimpleName()));
        Log.d("RecipeDetailsActivity", String.format("Showing details for '%s'", recipe.getRecipeName()));

        ActivityRecipeDetailsBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_recipe_details);

        ivRecipeImage = findViewById(R.id.ivRecipeImage);
        String imageUrl = recipe.getImage();
        Glide.with(getApplicationContext())
                .load(imageUrl)
                .into(ivRecipeImage);

        binding.setRecipe(recipe);
    }
}