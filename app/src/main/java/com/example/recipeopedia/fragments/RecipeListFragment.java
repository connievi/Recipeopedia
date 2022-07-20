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
import android.widget.EditText;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.recipeopedia.R;
import com.example.recipeopedia.adapters.RecipeAdapter;
import com.example.recipeopedia.RecipeKeys;
import com.example.recipeopedia.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class RecipeListFragment extends Fragment {
    public static final String TAG = "RecipeListFragment";
    private RecyclerView rvRecipes;
    protected RecipeAdapter recipeAdapter;
    protected List<Recipe> mRecipes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecipes = new ArrayList<>();
        rvRecipes = view.findViewById(R.id.rvRecipes);
        recipeAdapter = new RecipeAdapter(mRecipes);
        rvRecipes.setAdapter(recipeAdapter);
        rvRecipes.setLayoutManager(new LinearLayoutManager(getContext()));

        queryRecipes("chicken"); // keyword is CHICKEN to not overload API with so many requests

        EditText etSearch = view.findViewById(R.id.etSearch);
        // TODO: uncomment below for final product
        /*etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchWord = s.toString();
                queryRecipes(searchWord);
                filter(searchWord);
            }
        });*/
    }

    private void filter(String text) {
        ArrayList<Recipe> filteredList = new ArrayList<>();
        for (Recipe recipe : mRecipes) {
            if (recipe.getRecipeName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(recipe);
            }
        }
        recipeAdapter.setFilter(filteredList);
    }

    private void queryRecipes(String searchWord) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put(RecipeKeys.KEY_LIMIT, "5");
        params.put(RecipeKeys.KEY_PAGE, "0");
        params.put(RecipeKeys.KEY_TYPE, RecipeKeys.KEY_PUBLIC);
        params.put(RecipeKeys.KEY_APP_ID, RecipeKeys.APP_ID);
        params.put(RecipeKeys.KEY_APP_KEY, RecipeKeys.APP_KEY);
        params.put(RecipeKeys.KEY_QUERY, "chicken"); // TODO: change "chicken" to searchWord for final product
        client.get(RecipeKeys.KEY_URL_PREFIX, params, new JsonHttpResponseHandler()
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
                    mRecipes.addAll(Recipe.fromJsonArray(hits));
                    recipeAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Recipes: " + mRecipes.size());
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