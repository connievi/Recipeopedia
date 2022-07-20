package com.example.recipeopedia;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipeopedia.activities.RecipeDetailsActivity;
import com.example.recipeopedia.databinding.ItemRecipeBinding;
import com.example.recipeopedia.models.FavoriteRecipe;
import com.example.recipeopedia.models.Recipe;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    public static final String TAG = "RecipeAdapter";
    private List<Recipe> mRecipes;

    public RecipeAdapter(List<Recipe> recipes) {
        this.mRecipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Log.d(TAG, "onCreateViewHolder");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View recipeView = inflater.inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Log.d(TAG, "onBindViewHolder " + position);
        Recipe recipe = mRecipes.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public void setFilter(List<Recipe> filteredRecipes){
        mRecipes = new ArrayList<>();
        mRecipes.addAll(filteredRecipes);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {
        private ItemRecipeBinding binding;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bind(Recipe recipe)
        {
            binding.setRecipe(recipe);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION)
            {
                Recipe recipe = mRecipes.get(position);
                Intent intent = new Intent(v.getContext(), RecipeDetailsActivity.class);
                intent.putExtra(Recipe.class.getSimpleName(), Parcels.wrap(recipe));
                v.getContext().startActivity(intent);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION)
            {
                Recipe recipe = mRecipes.get(position);
                try {
                    saveRecipe(recipe.getRecipeName(), ParseUser.getCurrentUser(),
                            recipe.getImage(), recipe.getIngredients(), recipe.getInstructions());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }

        public void saveRecipe(String recipeName, ParseUser currentUser, String imageUrl,
                               String ingredients, String instructions) throws ParseException {
            ParseQuery<FavoriteRecipe> query = ParseQuery.getQuery(FavoriteRecipe.class);
            query.whereEqualTo(FavoriteRecipe.KEY_RECIPE_NAME, recipeName);
            if (query.count() > 0) {
                Toast.makeText(itemView.getContext(), "Recipe already saved!", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(itemView.getContext(), "Error while saving!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.i(TAG, "Recipe already saved recipe saved successfully");
                            Toast.makeText(itemView.getContext(), "Recipe saved!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
}