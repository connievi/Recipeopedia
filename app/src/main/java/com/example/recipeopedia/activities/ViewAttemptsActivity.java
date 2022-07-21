package com.example.recipeopedia.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipeopedia.R;
import com.example.recipeopedia.adapters.AttemptAdapter;
import com.example.recipeopedia.models.Attempt;
import com.example.recipeopedia.models.FavoriteRecipe;
import com.example.recipeopedia.models.Review;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class ViewAttemptsActivity extends AppCompatActivity {
    private Button btnPostAttempt;
    private EditText etYourAttempt;
    private TextView tvNoAttempts;
    private RecyclerView rvAttempts;
    private FavoriteRecipe favoriteRecipe;
    private SwipeRefreshLayout swipeContainer;
    protected AttemptAdapter attemptAdapter;
    protected List<Attempt> mAttempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attempts);

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                attemptAdapter.clear();
                queryAttempts();
                swipeContainer.setRefreshing(false);
            }
        });

        mAttempts = new ArrayList<>();
        attemptAdapter = new AttemptAdapter(mAttempts);
        rvAttempts = findViewById(R.id.rvAttempts);
        rvAttempts.setAdapter(attemptAdapter);
        rvAttempts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        tvNoAttempts = findViewById(R.id.tvNoAttempts);
        queryAttempts();

        favoriteRecipe = Parcels.unwrap(getIntent().getParcelableExtra(FavoriteRecipe.class.getSimpleName()));
        etYourAttempt = findViewById(R.id.etYourAttempt);
        btnPostAttempt = findViewById(R.id.btnPostAttempt);
        ParseUser currentUser = ParseUser.getCurrentUser();
        btnPostAttempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteRecipe = Parcels.unwrap(getIntent().getParcelableExtra(FavoriteRecipe.class.getSimpleName()));
                String description = etYourAttempt.getText().toString();
                if (description.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Description cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    try {
                        saveAttempt(favoriteRecipe.getRecipeName(), currentUser, description);
                        attemptAdapter.clear();
                        queryAttempts();
                        swipeContainer.setRefreshing(false);
                        Toast.makeText(getApplicationContext(), "Attempt posted!", Toast.LENGTH_SHORT).show();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void saveAttempt(String recipeName, ParseUser currentUser, String description) throws ParseException {
        Attempt attempt = new Attempt();
        attempt.setRecipeName(recipeName);
        attempt.setUser(currentUser);
        attempt.setAttempt(description);
        attempt.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    return;
                }
            }
        });
    }

    private void queryAttempts() {
        ParseQuery<Attempt> query = ParseQuery.getQuery(Attempt.class);
        query.include(Review.KEY_USER);
        favoriteRecipe = Parcels.unwrap(getIntent().getParcelableExtra(FavoriteRecipe.class.getSimpleName()));
        String recipeName = favoriteRecipe.getRecipeName();
        query.whereEqualTo(Attempt.KEY_RECIPE_NAME, recipeName);
        query.setLimit(20);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<Attempt>() {
            @Override
            public void done(List<Attempt> attempts, ParseException e) {
                if (e != null) {
                    return;
                }
                mAttempts.addAll(attempts);
                if (mAttempts.isEmpty()) {
                    tvNoAttempts.setVisibility(View.VISIBLE);
                }
                else {
                    tvNoAttempts.setVisibility(View.GONE);
                }
                attemptAdapter.notifyDataSetChanged();
            }
        });
    }
}