package com.example.recipeopedia.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeopedia.R;
import com.example.recipeopedia.activities.FavoriteRecipeDetailsActivity;
import com.example.recipeopedia.databinding.ItemAttemptBinding;
import com.example.recipeopedia.databinding.ItemFavoriteRecipeBinding;
import com.example.recipeopedia.models.Attempt;
import com.example.recipeopedia.models.FavoriteRecipe;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class AttemptAdapter extends RecyclerView.Adapter<AttemptAdapter.ViewHolder> {
    public static final String TAG = "AttemptAdapter";
    public List<Attempt> mAttempts;

    public AttemptAdapter() {
        mAttempts = new ArrayList<>();
    }

    @NonNull
    @Override
    public AttemptAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View recipeView = inflater.inflate(R.layout.item_attempt, parent, false);
        return new AttemptAdapter.ViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(@NonNull AttemptAdapter.ViewHolder holder, int position)
    {
        Attempt attempt = mAttempts.get(position);
        holder.bind(attempt);
    }

    @Override
    public int getItemCount() {
        return mAttempts.size();
    }

    public void clear() {
        mAttempts.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ItemAttemptBinding binding;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(Attempt attempt)
        {
            binding.setAttempt(attempt);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION)
            {
                Attempt attempt = mAttempts.get(position);
                Intent intent = new Intent(v.getContext(), FavoriteRecipeDetailsActivity.class);
                intent.putExtra(FavoriteRecipe.class.getSimpleName(), Parcels.wrap(attempt));
                v.getContext().startActivity(intent);
            }
        }
    }
}
