package com.oxootv.spagreen.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.leanback.app.VerticalGridSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.SearchOrbView;
import androidx.leanback.widget.VerticalGridPresenter;


import com.oxootv.spagreen.Config;
import com.oxootv.spagreen.NetworkInst;
import com.oxootv.spagreen.R;
import com.oxootv.spagreen.model.Movie;
import com.oxootv.spagreen.network.RetrofitClient;
import com.oxootv.spagreen.network.api.MovieApi;
import com.oxootv.spagreen.ui.BackgroundHelper;
import com.oxootv.spagreen.ui.activity.ErrorActivity;
import com.oxootv.spagreen.ui.activity.LeanbackActivity;
import com.oxootv.spagreen.ui.activity.SearchActivity;
import com.oxootv.spagreen.ui.activity.VideoDetailsActivity;
import com.oxootv.spagreen.ui.presenter.VerticalCardPresenter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MoviesFragment extends VerticalGridSupportFragment {

    public static final String MOVIE = "movie";
    private static final String TAG = MoviesFragment.class.getSimpleName();
    private static final int NUM_COLUMNS = 8;
    private int pageCount = 1;
    private boolean dataAvailable = true;

    private BackgroundHelper bgHelper;

    private List<Movie> movies = new ArrayList<>();
    private ArrayObjectAdapter mAdapter;

    private LeanbackActivity activity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        activity = (LeanbackActivity) getActivity();

        activity.hideLogo();

        setTitle(getResources().getString(R.string.movie));

        bgHelper = new BackgroundHelper(getActivity());

        setOnItemViewClickedListener(getDefaultItemViewClickedListener());
        setOnItemViewSelectedListener(getDefaultItemSelectedListener());

        // setup
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);

        mAdapter = new ArrayObjectAdapter(new VerticalCardPresenter(MOVIE));
        setAdapter(mAdapter);

        fetchMovieData(pageCount);

        //setupFragment();
    }

    // click listener
    private OnItemViewClickedListener getDefaultItemViewClickedListener() {
        return new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder viewHolder, Object o,
                                      RowPresenter.ViewHolder viewHolder2, Row row) {

                Movie movie = (Movie) o;

                Intent intent = new Intent(getActivity(), VideoDetailsActivity.class);
                intent.putExtra("id", movie.getVideosId());
                intent.putExtra("type", "movie");
                intent.putExtra("thumbImage", movie.getThumbnailUrl());

                ImageView imageView = ((ImageCardView) viewHolder.view).getMainImageView();
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        imageView, VideoDetailsFragment.TRANSITION_NAME).toBundle();
                startActivity(intent, bundle);


            }
        };
    }

    // selected listener for setting blur background each time when the item will select.
    protected OnItemViewSelectedListener getDefaultItemSelectedListener() {
        return new OnItemViewSelectedListener() {
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, final Object item,
                                       RowPresenter.ViewHolder rowViewHolder, Row row) {

                // pagination
                if (dataAvailable) {
                    int itemPos = mAdapter.indexOf(item);
                    if (itemPos == movies.size() - 1) {
                        pageCount++;
                        fetchMovieData(pageCount);
                    }
                }


                if (item instanceof Movie) {
                    bgHelper = new BackgroundHelper(getActivity());
                    bgHelper.prepareBackgroundManager();
                    bgHelper.startBackgroundTimer(((Movie) item).getThumbnailUrl());

                }

            }
        };
    }

    public void fetchMovieData(int pageCount) {

        if (!new NetworkInst(activity).isNetworkAvailable()) {
            Intent intent = new Intent(activity, ErrorActivity.class);
            startActivity(intent);
            activity.finish();
            return;
        }

        final SpinnerFragment mSpinnerFragment = new SpinnerFragment();
        final FragmentManager fm = getFragmentManager();
        fm.beginTransaction().add(R.id.custom_frame_layout, mSpinnerFragment).commit();

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        MovieApi api = retrofit.create(MovieApi.class);
        Call<List<Movie>> call = api.getMovies(Config.API_KEY, pageCount);
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                List<Movie> movieList = response.body();
                if (movieList.size() <= 0) {
                    dataAvailable = false;
                    if (pageCount != 2) {
                        Toast.makeText(activity, getResources().getString(R.string.no_more_data_found), Toast.LENGTH_SHORT).show();
                    }
                }

                for (Movie movie : movieList) {
                    mAdapter.add(movie);
                }

                mAdapter.notifyArrayItemRangeChanged(movieList.size() - 1, movieList.size() + movies.size());
                movies.addAll(movieList);
                //setAdapter(mAdapter);

                // hide the spinner
                fm.beginTransaction().remove(mSpinnerFragment).commitAllowingStateLoss();

            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                t.printStackTrace();
                // hide the spinner
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                fm.beginTransaction().remove(mSpinnerFragment).commitAllowingStateLoss();
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        movies = new ArrayList<>();
        pageCount = 1;
        dataAvailable = true;

    }


}
