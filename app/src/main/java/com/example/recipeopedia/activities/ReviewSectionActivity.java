package com.example.recipeopedia.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recipeopedia.R;
import com.example.recipeopedia.adapters.ReviewAdapter;
import com.example.recipeopedia.models.Recipe;
import com.example.recipeopedia.models.Review;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewSectionActivity extends AppCompatActivity {
    public static final String TAG = "ReviewSectionActivity";
    private Recipe recipe;
    private Button btnPostReview;
    private EditText etReview;
    private RecyclerView rvReviews;
    private SwipeRefreshLayout swipeContainer;
    protected ReviewAdapter reviewAdapter;
    protected List<Review> mReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_section);

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reviewAdapter.clear();
                queryReviews();
                swipeContainer.setRefreshing(false);
            }
        });

        mReviews = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(mReviews);
        rvReviews = findViewById(R.id.rvReviews);
        rvReviews.setAdapter(reviewAdapter);
        rvReviews.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        queryReviews();
        setUpLeaveReview();
    }

    private void saveReview(String recipeId, ParseUser currentUser, String username, String comment) throws ParseException {
        Review review = new Review();
        review.setRecipeId(recipeId);
        review.setUsername(username);
        review.setUser(currentUser);
        review.setReview(comment);
        review.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    return;
                }
            }
        });
    }

    private void setUpLeaveReview() {
        etReview = findViewById(R.id.etReview);
        btnPostReview = findViewById(R.id.btnPostReview);
        btnPostReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipe = Parcels.unwrap(getIntent().getParcelableExtra(Recipe.class.getSimpleName()));
                String recipeId = recipe.getRecipeId();
                ParseUser currentUser = ParseUser.getCurrentUser();
                String username = currentUser.getUsername();
                String comment = etReview.getText().toString();
                if (comment.isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.review_empty, Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    try {
                        saveReview(recipeId, currentUser, username, comment);
                        reviewAdapter.clear();
                        queryReviews();
                        swipeContainer.setRefreshing(false);
                        Toast.makeText(getApplicationContext(), R.string.review_posted, Toast.LENGTH_SHORT).show();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void queryReviews() {
        ParseQuery<Review> query = ParseQuery.getQuery(Review.class);
        query.include(Review.KEY_USER);
        recipe = Parcels.unwrap(getIntent().getParcelableExtra(Recipe.class.getSimpleName()));
        String recipeId = recipe.getRecipeId();
        query.whereEqualTo(Review.KEY_RECIPE_ID, recipeId);
        query.setLimit(20);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<Review>() {
            @Override
            public void done(List<Review> reviews, ParseException e) {
                if (e != null) {
                    return;
                }
                mReviews.addAll(reviews);
                reviewAdapter.notifyDataSetChanged();
            }
        });
    }
}