package com.example.recipeopedia.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipeopedia.R;
import com.example.recipeopedia.RecipeAdapter;
import com.example.recipeopedia.models.Recipe;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class SavedRecipesFragment extends Fragment {
    public static final String TAG = "SavedRecipesFragment";
    private RecyclerView rvSavedRecipes;
    protected RecipeAdapter recipeAdapter;
    protected List<Recipe> mRecipes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecipes = new ArrayList<>();
        rvSavedRecipes = view.findViewById(R.id.rvSavedRecipes);
        recipeAdapter = new RecipeAdapter(mRecipes);
        rvSavedRecipes.setAdapter(recipeAdapter);
        rvSavedRecipes.setLayoutManager(new LinearLayoutManager(getContext()));
        //querySavedRecipes();
    }

    /*protected void querySavedRecipes() {
        ParseQuery<Recipe> query = ParseQuery.getQuery(Recipe.class);
        query.include(Recipe.KEY_USER);
        query.whereEqualTo(Recipe.KEY_USER, ParseUser.getCurrentUser());
        query.addDescendingOrder(Recipe.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Recipe>() {
            @Override
            public void done(List<Recipe> recipes, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting recipes", e);
                    return;
                }
                for (Recipe recipe: recipes) {
                    Log.i(TAG, "Recipe: " + recipe.getRecipeName());
                }
                mRecipes.addAll(recipes);
                recipeAdapter.notifyDataSetChanged();
            }
        });
    }*/
}