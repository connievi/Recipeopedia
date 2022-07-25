package com.example.recipeopedia;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeopedia.adapters.FavoriteRecipeAdapter;

public class ItemMoveCallback extends ItemTouchHelper.Callback {
    private final FavoriteRecipeAdapter mAdapter;

    public ItemMoveCallback(FavoriteRecipeAdapter favoriteRecipeAdapter) {
        mAdapter = favoriteRecipeAdapter;
    }

    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        mAdapter.onRowMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder,
                                  int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof FavoriteRecipeAdapter.ViewHolder) {
                FavoriteRecipeAdapter.ViewHolder myViewHolder = (FavoriteRecipeAdapter.ViewHolder) viewHolder;
                mAdapter.onRowSelected(myViewHolder);
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (viewHolder instanceof FavoriteRecipeAdapter.ViewHolder) {
            FavoriteRecipeAdapter.ViewHolder myViewHolder = (FavoriteRecipeAdapter.ViewHolder) viewHolder;
            mAdapter.onRowClear(myViewHolder);
        }
    }

    public interface ItemTouchHelperContract {
        void onRowMoved(int fromPosition, int toPosition);
        void onRowSelected(FavoriteRecipeAdapter.ViewHolder myViewHolder);
        void onRowClear(FavoriteRecipeAdapter.ViewHolder myViewHolder);
    }
}
