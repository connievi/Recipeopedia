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

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.recipeopedia.R;
import com.example.recipeopedia.RecipeAdapter;
import com.example.recipeopedia.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class RecipeListFragment extends Fragment {
    public static final String TAG = "RecipeListFragment";
    public static final String URL_PREFIX = "https://api.edamam.com/api/recipes/v2";
    public static final String APP_ID = "ee1f2fe0";
    public static final String APP_KEY = "ec8cceba866280e53c0c9f2300c17b1b";
    private RecyclerView rvRecipes;
    protected RecipeAdapter adapter;
    protected List<Recipe> recipes;


    public RecipeListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvRecipes = view.findViewById(R.id.rvRecipes);
        recipes = new ArrayList<>();

        RecipeAdapter recipeAdapter = new RecipeAdapter(getContext(), recipes);
        rvRecipes.setAdapter(recipeAdapter);
        rvRecipes.setLayoutManager(new LinearLayoutManager(getContext()));

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("limit", "5");
        params.put("page", "0");
        params.put("type", "public");
        params.put("app_id", APP_ID);
        params.put("app_key", APP_KEY);
        params.put("q", "chicken"); // need to change this so users can search and query
        client.get(URL_PREFIX, params, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json)
            {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try
                {
                    JSONArray hits = jsonObject.getJSONArray("hits");
                    Log.i(TAG, "Hits: " + hits.toString());
                    recipes.addAll(Recipe.fromJsonArray(hits));
                    recipeAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Recipes: " + recipes.size());
                }
                catch (JSONException e)
                {
                    Log.e(TAG, "Hit json exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e("ERROR", response);
            }
        });
    }
}