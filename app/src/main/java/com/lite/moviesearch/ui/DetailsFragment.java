package com.lite.moviesearch.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.lite.moviesearch.R;
import com.lite.moviesearch.models.MovieDetail;
import com.lite.moviesearch.util.AppConstants;
import com.lite.moviesearch.viewmodels.MovieSearchViewModel;

public class DetailsFragment extends Fragment {

    private MovieSearchViewModel mMovieModel;
    private TextView titleView;
    private TextView plotView;
    private TextView directorView;
    private TextView actorView;
    private ImageView posterView;
    private String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieModel = ViewModelProviders.of(getActivity()).get(MovieSearchViewModel.class);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
            mMovieModel.loadLDetails(id);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details, container, false);
        posterView = (ImageView) view.findViewById(R.id.movieimage);
        titleView = (TextView) view.findViewById(R.id.title);
        plotView = (TextView) view.findViewById(R.id.title2);
        directorView = (TextView) view.findViewById(R.id.title3);
        actorView = (TextView) view.findViewById(R.id.title4);
        mMovieModel.getDetailLiveData().observe(this, new Observer<MovieDetail>() {
            @Override
            public void onChanged(MovieDetail movieDetail) {
                titleView.setText(movieDetail.getTitle());
                plotView.setText(movieDetail.getPlot());
                directorView.setText(movieDetail.getDirector());
                actorView.setText(movieDetail.getActors());
                RequestManager requestManager = AppConstants.initGlide(getActivity());
                requestManager.load(movieDetail.getPoster()).into(posterView);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(DetailsFragment.class.getSimpleName(), "onDestroyView: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(DetailsFragment.class.getSimpleName(), "onDetach: ");
    }
}
