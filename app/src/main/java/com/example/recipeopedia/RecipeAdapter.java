package com.example.recipeopedia;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipeopedia.activities.RecipeDetailsActivity;
import com.example.recipeopedia.models.Recipe;

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
        View recipeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tvRecipeName;
        ImageView ivRecipeImage;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tvRecipeName = itemView.findViewById(R.id.tvRecipeName);
            ivRecipeImage = itemView.findViewById(R.id.ivRecipeImage);
            itemView.setOnClickListener(this);
        }

        public void bind(Recipe recipe)
        {
            tvRecipeName.setText(recipe.getRecipeName());
            String imageUrl = recipe.getImage();
            Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .into(ivRecipeImage);
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
    }
}