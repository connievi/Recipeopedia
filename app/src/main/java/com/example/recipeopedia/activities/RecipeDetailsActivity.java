package com.example.recipeopedia.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recipeopedia.R;
import com.example.recipeopedia.models.Recipe;

import org.parceler.Parcels;

public class RecipeDetailsActivity extends AppCompatActivity {
    public static final String TAG = "RecipeDetailsActivity";
    private Recipe recipe;
    private TextView tvRecipeName, tvIngredients, tvInstructions;
    private ImageView ivRecipeImage;

    /*TextView nutrition;
    TextView mealType;
    TextView cuisineType;
    TextView dishType;
    TextView dietLabels;
    TextView healthLabels;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        tvRecipeName = findViewById(R.id.tvRecipeName);
        tvIngredients = findViewById(R.id.tvIngredients);
        tvInstructions = findViewById(R.id.tvInstructions);
        ivRecipeImage = findViewById(R.id.ivRecipeImage);

        recipe = Parcels.unwrap(getIntent().getParcelableExtra(Recipe.class.getSimpleName()));
        Log.d("RecipeDetailsActivity", String.format("Showing details for '%s'", recipe.getRecipeName()));

        tvRecipeName.setText(recipe.getRecipeName());
        tvIngredients.setText(recipe.getIngredients());
        tvInstructions.setText(recipe.getInstructions());

        String imageUrl = recipe.getImage();
        Glide.with(getApplicationContext())
                .load(imageUrl)
                .into(ivRecipeImage);
    }
}