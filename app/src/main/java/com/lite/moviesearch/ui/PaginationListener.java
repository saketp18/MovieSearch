package com.lite.moviesearch.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager layoutManager;
    private int visibleItemCount, totalItemCount, firstVisibleItemPosition, previousTotalItem = 0, viewThreshold = 10;
    private boolean isLoading;

    public PaginationListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = layoutManager.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >=
                    totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems();
            }
        }
    }

    protected abstract void loadMoreItems();
    public abstract boolean isLastPage();
    public abstract boolean isLoading();
}
