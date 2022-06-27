package com.example.recipeopedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.recipeopedia.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class RecipeListActivity extends AppCompatActivity {

    public static final String TAG = "RecipeListActivity";
    public static final String URL_PREFIX = "https://api.edamam.com/api/recipes/v2";
    public static final String APP_ID = "ee1f2fe0";
    public static final String APP_KEY = "ec8cceba866280e53c0c9f2300c17b1b";

    public List<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        RecyclerView rvRecipes = findViewById(R.id.rvRecipes);
        recipes = new ArrayList<>();

        RecipeAdapter recipeAdapter = new RecipeAdapter(this, recipes);
        rvRecipes.setAdapter(recipeAdapter);
        rvRecipes.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("limit", "5");
        params.put("page", "0");
        params.put("type", "public");
        params.put("app_id", APP_ID);
        params.put("app_key", APP_KEY);
        params.put("q", "chicken");
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