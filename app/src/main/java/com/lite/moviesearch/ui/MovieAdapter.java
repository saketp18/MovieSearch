package com.lite.moviesearch.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.lite.moviesearch.R;
import com.lite.moviesearch.models.MovieResponse;
import com.lite.moviesearch.models.MovieSearch;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieListHolder>{

    private List<MovieSearch> movieSearchList;
    private RequestManager mRequestManager;
    private OnItemClickListener itemClickListener;
    private State mState;

    public MovieAdapter(RequestManager requestManager, OnItemClickListener onItemClickListener, State state) {
        this.mRequestManager = requestManager;
        this.itemClickListener = onItemClickListener;
        this.mState = state;
        movieSearchList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MovieListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchitem, parent, false);
        MovieListHolder holder = new MovieListHolder(view, itemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListHolder holder, int position) {
        if (movieSearchList.size() <= 0)
            return;
        holder.movieTitle.setText(movieSearchList.get(position).getTitle());
        holder.movieYear.setText(movieSearchList.get(position).getYear());
        if (mState == State.SEARCH)
            holder.movieBookmark.setText("BookMark");
        else
            holder.movieBookmark.setText("Remove Bookmark");
        mRequestManager.load(movieSearchList.get(position).getPoster()).into(holder.moviewImage);
    }

    @Override
    public int getItemCount() {
        return movieSearchList.size();
    }

    public void clear() {
        movieSearchList.clear();
    }

    public void removeData(MovieSearch movieSearch) {
        movieSearchList.remove(movieSearch);
        notifyDataSetChanged();
    }

    public boolean isDataEmpty() {
        return movieSearchList.size() > 0 ? false : true;
    }

    public void setMovieResponse(MovieResponse movieResponse){
        if (movieResponse.getSearch() != null) {
            movieSearchList.addAll(movieResponse.getSearch());
            notifyDataSetChanged();
        }
    }

    public void setData(List<MovieSearch> movieResponses) {
        if (movieResponses != null) {
            movieSearchList.clear();
            movieSearchList.addAll(movieResponses);
            notifyDataSetChanged();
        }
    }

    public enum State {
        SEARCH,
        BOOKMARK
    }

    interface OnItemClickListener {

        void onItemClick(MovieSearch movieSearch);

        void onAddBookMark(MovieSearch movieSearch);

        void onRemoveBookMark(MovieSearch movieSearch);
    }

    protected class MovieListHolder extends RecyclerView.ViewHolder {

        protected ImageView moviewImage;
        protected TextView movieTitle;
        protected TextView movieYear;
        protected TextView movieBookmark;

        public MovieListHolder(@NonNull final View itemView, @NonNull final OnItemClickListener itemClick) {
            super(itemView);
            moviewImage = (ImageView) itemView.findViewById(R.id.movieimage);
            movieTitle = (TextView) itemView.findViewById(R.id.title);
            movieYear = (TextView) itemView.findViewById(R.id.year);
            movieBookmark = (TextView) itemView.findViewById(R.id.bookmark);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClick.onItemClick(movieSearchList.get(getAdapterPosition()));
                }
            });

            movieBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mState == State.SEARCH)
                        itemClick.onAddBookMark(movieSearchList.get(getAdapterPosition()));
                    else
                        itemClick.onRemoveBookMark(movieSearchList.get(getAdapterPosition()));
                }
            });
        }
    }
}
