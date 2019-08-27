package com.lite.moviesearch.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lite.moviesearch.R;
import com.lite.moviesearch.models.MovieResponse;
import com.lite.moviesearch.models.MovieSearch;
import com.lite.moviesearch.util.AppConstants;
import com.lite.moviesearch.viewmodels.MovieSearchViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener {

    private final static String initalquery = "Friends";
    private static String loadquery;
    private static int page = 1;
    private static boolean isDetailShown = false;
    private SearchView mSearchView;
    private RecyclerView mMovieList;
    private RecyclerView mBookMarkList;
    private FrameLayout frameLayout;
    private LinearLayoutManager mLinearLayoutManager;
    private LinearLayoutManager mBookMarkLayoutManager;
    private MovieAdapter mMovieAdapter;
    private MovieAdapter mBookMarkAdapter;
    private MovieSearchViewModel mMovieModel;
    private FragmentTransaction fragmentTransaction;
    private String TAG = MainActivity.class.getName();
    private boolean isLastPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMovieModel = ViewModelProviders.of(this).get(MovieSearchViewModel.class);
        setUpViews(savedInstanceState);
        loadMovieData(loadquery, page);
        subScribeObservers();
    }

    private void setUpViews(Bundle savedInstanceState) {
        frameLayout = (FrameLayout) findViewById(R.id.detailsfragment);
        if (savedInstanceState != null) {
            isDetailShown = savedInstanceState.getBoolean("isfragmentvisible", false);
            int visibility = isDetailShown ? View.VISIBLE : View.GONE;
            frameLayout.setVisibility(visibility);
        }
        initRecyclerView();
        initSearchView();
        initBookMarks();
    }

    private void initRecyclerView() {
        mMovieAdapter = new MovieAdapter(AppConstants.initGlide(this), this, MovieAdapter.State.SEARCH);
        mMovieAdapter.clear();
        mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mMovieList = (RecyclerView) findViewById(R.id.moviepage);
        mMovieList.setItemAnimator(null);
        mMovieList.setLayoutManager(mLinearLayoutManager);
        mMovieList.setAdapter(mMovieAdapter);
        mMovieList.addOnScrollListener(new PaginationListener(mLinearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                page++;
                loadMovieData(loadquery, page);
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return false;
            }
        });
    }

    private void initSearchView() {
        mSearchView = (SearchView) findViewById(R.id.searchbar);
        mSearchView.setQueryHint(getResources().getString(R.string.queryhint));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadquery = query;
                page = 1;
                mMovieAdapter.clear();
                isLastPage = false;
                loadMovieData(loadquery, page);
                Log.d(TAG, "onQueryTextSubmit: " + query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange: " + newText);
                return false;
            }
        });
    }

    private void initBookMarks() {
        mBookMarkList = (RecyclerView) findViewById(R.id.bookmarks);
        mBookMarkAdapter = new MovieAdapter(AppConstants.initGlide(this), this, MovieAdapter.State.BOOKMARK);
        mBookMarkLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mBookMarkList.setLayoutManager(mBookMarkLayoutManager);
        mBookMarkList.setAdapter(mBookMarkAdapter);
    }

    private void loadMovieData(String query, int page) {
        if (query == null)
            query = initalquery;
        mMovieModel.loadData(query, page);

    }

    private void subScribeObservers() {
        mMovieModel.getMovieResponseLiveData().observe(this, new Observer<MovieResponse>() {
            @Override
            public void onChanged(MovieResponse movieResponse) {
                if(movieResponse.getResponse().equals("True"))
                    mMovieAdapter.setMovieResponse(movieResponse);
                else if(movieResponse.getResponse().equals("False")) {
                    isLastPage = true;
                    Toast.makeText(getApplicationContext(), "Results finished!!!", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getApplicationContext(), "Please check interent connection!!!", Toast.LENGTH_SHORT).show();
            }
        });
        mMovieModel.getListLiveData().observe(this, new Observer<List<MovieSearch>>() {
            @Override
            public void onChanged(List<MovieSearch> movieSearches) {
                mBookMarkList.setVisibility(View.VISIBLE);
                mBookMarkAdapter.setData(movieSearches);
                mBookMarkList.smoothScrollToPosition(mBookMarkAdapter.getItemCount());
            }
        });
    }

    @Override
    public void onItemClick(MovieSearch movieSearch) {
        if (isDetailShown)
            return;
        frameLayout.setVisibility(View.VISIBLE);
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", movieSearch.getTitle());
        bundle.putString("year", movieSearch.getYear());
        bundle.putString("poster", movieSearch.getPoster());
        detailsFragment.setArguments(bundle);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(frameLayout.getId(), detailsFragment);
        fragmentTransaction.addToBackStack("detailsfragment");
        fragmentTransaction.commit();
        isDetailShown = true;
    }

    @Override
    public void onAddBookMark(MovieSearch movieSearch) {
        mMovieModel.insertData(movieSearch);
        Toast.makeText(getApplicationContext(), "BookMark Added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveBookMark(MovieSearch movieSearch) {
        Toast.makeText(getApplicationContext(), "BookMark Removed", Toast.LENGTH_SHORT).show();
        mMovieModel.removedata(movieSearch);
        mBookMarkAdapter.removeData(movieSearch);
        if (mBookMarkAdapter.isDataEmpty()) {
            mBookMarkList.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isDetailShown) {
            isDetailShown = false;
            frameLayout.setVisibility(View.GONE);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isfragmentvisible", isDetailShown);
    }
}
