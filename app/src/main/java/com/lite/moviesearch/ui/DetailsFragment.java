package com.lite.moviesearch.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.RequestManager;
import com.lite.moviesearch.R;
import com.lite.moviesearch.util.AppConstants;

public class DetailsFragment extends Fragment {

    private TextView titleView;
    private TextView yearView;
    private TextView option;
    private ImageView posterView;
    private String title;
    private String year;
    private String poster;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            title = bundle.getString("title");
            year = bundle.getString("year");
            poster = bundle.getString("poster");
        } else if (savedInstanceState != null) {
            title = savedInstanceState.getString("title");
            year = savedInstanceState.getString("year");
            poster = savedInstanceState.getString("poster");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchitem, container, false);
        posterView = (ImageView) view.findViewById(R.id.movieimage);
        titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(title);
        yearView = (TextView) view.findViewById(R.id.year);
        yearView.setText(year);
        option = (TextView) view.findViewById(R.id.bookmark);
        option.setVisibility(View.GONE);
        RequestManager requestManager = AppConstants.initGlide(getActivity());
        requestManager.load(poster).into(posterView);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title", title);
        outState.putString("year", year);
        outState.putString("poster", poster);
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
