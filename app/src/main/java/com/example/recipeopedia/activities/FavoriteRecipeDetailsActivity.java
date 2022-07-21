package com.example.recipeopedia.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class FavoriteRecipeDetailsActivity extends AppCompatActivity {
    public static final String TAG = "FavoriteRecipeDetailsActivity";
    private FavoriteRecipe favoriteRecipe;
    private Button btnViewAttempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipe_details);

        favoriteRecipe = Parcels.unwrap(getIntent().getParcelableExtra(FavoriteRecipe.class.getSimpleName()));
        ActivityFavoriteRecipeDetailsBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_favorite_recipe_details);
        binding.setImageUrl(favoriteRecipe.getImage());
        binding.setFavoriteRecipe(favoriteRecipe);

        btnViewAttempts = findViewById(R.id.btnViewAttempts);
        btnViewAttempts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewAttemptsActivity.class);
                intent.putExtra(FavoriteRecipe.class.getSimpleName(), Parcels.wrap(favoriteRecipe));
                v.getContext().startActivity(intent);
            }
        });
    }
}