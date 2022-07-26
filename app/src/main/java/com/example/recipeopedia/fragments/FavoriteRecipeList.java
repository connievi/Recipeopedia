package com.example.recipeopedia.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recipeopedia.adapters.FavoriteRecipeAdapter;
import com.example.recipeopedia.R;
import com.example.recipeopedia.models.FavoriteRecipe;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class FavoriteRecipeList extends Fragment {
    public static final String TAG = "FavoriteRecipeList";
    private RecyclerView rvFavoriteRecipes;
    private TextView tvNoRecipesSaved;
    protected FavoriteRecipeAdapter favoriteRecipeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_recipe_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFavoriteRecipes = view.findViewById(R.id.rvFavoriteRecipes);
        favoriteRecipeAdapter = new FavoriteRecipeAdapter();
        rvFavoriteRecipes.setAdapter(favoriteRecipeAdapter);
        rvFavoriteRecipes.setLayoutManager(new LinearLayoutManager(getContext()));
        tvNoRecipesSaved = view.findViewById(R.id.tvNoRecipesSaved);
        querySavedRecipes();
    }

    protected void querySavedRecipes() {
        ParseQuery<FavoriteRecipe> query = ParseQuery.getQuery(FavoriteRecipe.class);
        query.include(FavoriteRecipe.KEY_USER);
        query.whereEqualTo(FavoriteRecipe.KEY_USER, ParseUser.getCurrentUser());
        query.addDescendingOrder(FavoriteRecipe.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<FavoriteRecipe>() {
            @Override
            public void done(List<FavoriteRecipe> favoriteRecipes, ParseException e) {
                if (e != null) {
                    return;
                }
                favoriteRecipeAdapter.mFavoriteRecipes.addAll(favoriteRecipes);
                if (favoriteRecipeAdapter.mFavoriteRecipes.isEmpty()) {
                    tvNoRecipesSaved.setVisibility(View.VISIBLE);
                }
                else {
                    tvNoRecipesSaved.setVisibility(View.GONE);
                }
                favoriteRecipeAdapter.notifyDataSetChanged();
            }
        });
    }
}