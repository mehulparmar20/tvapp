package com.yahumott.tvapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import androidx.leanback.widget.VerticalGridPresenter;

import com.yahumott.tvapp.NetworkInst;
import com.yahumott.tvapp.R;
import com.yahumott.tvapp.model.Movie;
import com.yahumott.tvapp.network.RetrofitClient;
import com.yahumott.tvapp.network.api.MovieList;
import com.yahumott.tvapp.network.api.TvSeriesApi;
import com.yahumott.tvapp.ui.BackgroundHelper;
import com.yahumott.tvapp.ui.activity.ErrorActivity;
import com.yahumott.tvapp.ui.activity.LeanbackActivity;
import com.yahumott.tvapp.ui.activity.VideoDetailsActivity;
import com.yahumott.tvapp.ui.presenter.VerticalCardPresenter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TvSeriesFragment extends VerticalGridSupportFragment {

    public static final String TV_SERIES = "tvSeries";
    private static final String TAG = TvSeriesFragment.class.getSimpleName();
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

        setTitle(getResources().getString(R.string.tv_series));

        bgHelper = new BackgroundHelper(getActivity());

        setOnItemViewClickedListener(getDefaultItemViewClickedListener());
        setOnItemViewSelectedListener(getDefaultItemSelectedListener());

        // setup
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);

        mAdapter = new ArrayObjectAdapter(new VerticalCardPresenter(TV_SERIES));
        setAdapter(mAdapter);

        fetchTvSereisData(pageCount);

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
                intent.putExtra("type", "tvseries");
                intent.putExtra("thumbImage", movie.getThumbnailUrltv());
                //poster transition
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
                        fetchTvSereisData(pageCount);
                    }
                }

                //Log.d("iamge url: ------------------------------", itemPos+" : "+ movies.size());
                // change the background color when the item will select
                if (item instanceof Movie) {
                    bgHelper = new BackgroundHelper(getActivity());
                    bgHelper.prepareBackgroundManager();
                    bgHelper.startBackgroundTimer(((Movie) item).getThumbnailUrltv());

                }

            }
        };
    }

    public void fetchTvSereisData(int pageCount) {

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
        TvSeriesApi api = retrofit.create(TvSeriesApi.class);
        Call<MovieList> call = api.getTvSeries();
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.code() == 200) {
                    Log.d(TAG, response.toString());
                    movies = response.body().getResulttv();
                    Log.d(TAG, movies.toString());
                    if (movies.size() == 0) {
                        dataAvailable = false;
                        if (pageCount != 2) {
                            Toast.makeText(activity, "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    for (Movie movie : movies) {
                        Log.d(TAG, movie.getThumbnailUrltv());
                        mAdapter.add(movie);
                    }

                    mAdapter.notifyArrayItemRangeChanged(movies.size() - 1, movies.size() + movies.size());
                    movies.addAll(movies);
                    //setAdapter(mAdapter);

                    // hide the spinner
                    fm.beginTransaction().remove(mSpinnerFragment).commitAllowingStateLoss();

                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                t.printStackTrace();
                // hide the spinner
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
