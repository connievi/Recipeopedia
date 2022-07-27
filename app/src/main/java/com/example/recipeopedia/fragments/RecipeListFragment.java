package com.example.recipeopedia.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvRecipes = view.findViewById(R.id.rvRecipes);
        recipeAdapter = new RecipeAdapter();
        rvRecipes.setAdapter(recipeAdapter);
        rvRecipes.setLayoutManager(new LinearLayoutManager(getContext()));

        EditText etSearch = view.findViewById(R.id.etSearch);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (actionId == EditorInfo.IME_NULL)) {
                    queryRecipes(etSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });

        /* NOTE:
        * Rather than using the TextWatcher API that constantly queries the API with each character
        * typed into the EditText search bar, I decided to switch to an onEditorActionListener that
        * will only query the API after users type in a search word and hit enter. Despite using the
        * filtering algorithm, querying through the TextWatcher still made too many queries and took
        * too long to load recipes. The code for the TextWatcher is commented out directly below,
        * and the filtering algorithm is still in my code.
        */

/*        etSearch.addTextChangedListener(new TextWatcher() {
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
        for (Recipe recipe : recipeAdapter.mRecipes) {
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
        params.put(RecipeKeys.KEY_QUERY, searchWord);
        client.get(RecipeKeys.KEY_URL_PREFIX, params, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json)
            {
                JSONObject jsonObject = json.jsonObject;
                try
                {
                    JSONArray hits = jsonObject.getJSONArray("hits");
                    recipeAdapter.mRecipes.addAll(Recipe.fromJsonArray(hits));
                    recipeAdapter.notifyDataSetChanged();
                }
                catch (JSONException e)
                {
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
            }
        });
    }
}