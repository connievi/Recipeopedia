package com.example.recipeopedia.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeopedia.ItemMoveCallback;
import com.example.recipeopedia.R;
import com.example.recipeopedia.activities.FavoriteRecipeDetailsActivity;
import com.example.recipeopedia.databinding.ItemFavoriteRecipeBinding;
import com.example.recipeopedia.models.FavoriteRecipe;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoriteRecipeAdapter extends RecyclerView.Adapter<FavoriteRecipeAdapter.ViewHolder>
        implements ItemMoveCallback.ItemTouchHelperContract {
    public static final String TAG = "FavoriteRecipeAdapter";
    public List<FavoriteRecipe> mFavoriteRecipes;

    public FavoriteRecipeAdapter() {
        mFavoriteRecipes = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View recipeView = inflater.inflate(R.layout.item_favorite_recipe, parent, false);
        return new ViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteRecipeAdapter.ViewHolder holder, int position)
    {
        FavoriteRecipe favoriteRecipe = mFavoriteRecipes.get(position);
        holder.bind(favoriteRecipe);
    }

    @Override
    public int getItemCount() {
        return mFavoriteRecipes.size();
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mFavoriteRecipes, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mFavoriteRecipes, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRowSelected(ViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.GRAY);

    }

    @Override
    public void onRowClear(ViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.WHITE);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public View rowView;
        private ItemFavoriteRecipeBinding binding;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            rowView = itemView;
            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(FavoriteRecipe favoriteRecipe)
        {
            binding.setFavoriteRecipe(favoriteRecipe);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION)
            {
                FavoriteRecipe favoriteRecipe = mFavoriteRecipes.get(position);
                Intent intent = new Intent(v.getContext(), FavoriteRecipeDetailsActivity.class);
                intent.putExtra(FavoriteRecipe.class.getSimpleName(), Parcels.wrap(favoriteRecipe));
                v.getContext().startActivity(intent);
            }
        }
    }
}
