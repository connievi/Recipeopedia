package com.example.recipeopedia.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeopedia.R;
import com.example.recipeopedia.databinding.ItemReviewBinding;
import com.example.recipeopedia.models.FavoriteRecipe;
import com.example.recipeopedia.models.Recipe;
import com.example.recipeopedia.models.Review;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    public static final String TAG = "ReviewAdapter";
    private List<Review> mReviews;

    public ReviewAdapter(List<Review> reviews) {
        this.mReviews = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder " + position);
        Review review = mReviews.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public void clear() {
        mReviews.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemReviewBinding binding;
        private TextView tvCreatedAt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(Review review) {
            tvCreatedAt.setText(getRelativeTime());
            binding.setReview(review);
        }

        public String getRelativeTime() {
            int position = getAdapterPosition();
            Review review = mReviews.get(position);
            Date createdAt = review.getCreatedAt();
            SimpleDateFormat ft = new SimpleDateFormat("MMM d, yyyy 'at' hh:mm a");
            ft.setLenient(true);
            return ft.format(createdAt);
        }
    }
}
