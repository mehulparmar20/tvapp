package com.yahumott.tvapp.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.leanback.app.VerticalGridSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.VerticalGridPresenter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.yahumott.tvapp.Config;
import com.yahumott.tvapp.Constants;
import com.yahumott.tvapp.NetworkInst;
import com.yahumott.tvapp.utils.PreferenceUtils;
import com.yahumott.tvapp.R;
import com.yahumott.tvapp.model.CommonModels;
import com.yahumott.tvapp.model.Movie;
import com.yahumott.tvapp.model.VideoContent;
import com.yahumott.tvapp.network.RetrofitClient;
import com.yahumott.tvapp.network.api.FavouriteApi;
import com.yahumott.tvapp.ui.BackgroundHelper;
import com.yahumott.tvapp.ui.activity.ErrorActivity;
import com.yahumott.tvapp.ui.activity.LeanbackActivity;
import com.yahumott.tvapp.ui.activity.VideoDetailsActivity;
import com.yahumott.tvapp.ui.presenter.VerticalCardPresenter;
import com.yahumott.tvapp.utils.ToastMsg;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class FavouriteFragment extends VerticalGridSupportFragment {

    public static final String FAVORITE = "favorite";

    private static final String TAG = FavouriteFragment.class.getSimpleName();
    private static final int NUM_COLUMNS = 8;

    private BackgroundHelper bgHelper;

    private LinkedHashMap<String, List<VideoContent>> mVideoLists = null;
    private ArrayObjectAdapter mAdapter;

    private LeanbackActivity activity;
    private List<Movie> movies = new ArrayList<>();
    private boolean dataAvailable;
    private int pageCount = 1;
    String token ="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        activity = (LeanbackActivity) getActivity();

        activity.hideLogo();

        setTitle(getResources().getString(R.string.favorite));
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.USER_LOGIN_STATUS, MODE_PRIVATE);
        token = prefs.getString("access_token","");
        Log.d("token", token);
        bgHelper = new BackgroundHelper(getActivity());

        setOnItemViewClickedListener(getDefaultItemViewClickedListener());
        setOnItemViewSelectedListener(getDefaultItemSelectedListener());

        setupFragment();
    }

    private void setupFragment() {
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);

        mAdapter = new ArrayObjectAdapter(new VerticalCardPresenter(FAVORITE));
        setAdapter(mAdapter);

        fetchFavouriteData(token);

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
                if (movie.getIsTvseries().equals("0")) {
                    intent.putExtra("type", "movie");
                } else {
                    intent.putExtra("type", "tvseries");
                }

                intent.putExtra("thumbImage", movie.getThumbnailUrl());
                startActivity(intent);
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
                        fetchFavouriteData(token);
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


    public void fetchFavouriteData(String token) {

        if (!new NetworkInst(activity).isNetworkAvailable()) {
            Intent intent = new Intent(activity, ErrorActivity.class);
            startActivity(intent);
            activity.finish();
            return;
        }

        final SpinnerFragment mSpinnerFragment = new SpinnerFragment();
        final FragmentManager fm = getFragmentManager();
        fm.beginTransaction().add(R.id.custom_frame_layout, mSpinnerFragment).commit();


        String userId = PreferenceUtils.getUserId(getContext());

        Retrofit retrofit = RetrofitClient.getRetrofitInstance2(token);
        FavouriteApi api = retrofit.create(FavouriteApi.class);
        Call<List<Movie>> call = api.getFavoriteList(userId);
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, retrofit2.Response<List<Movie>> response) {
                Log.d("response", response.toString());
                List<Movie> movieList = response.body();
                if (movieList.size() <= 0) {
                    dataAvailable = false;
                    Toast.makeText(getContext(),  getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
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

    private void getData(String url, int pageNum) {

        String fullUrl = url + pageNum;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, fullUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.e("LOG::", String.valueOf(response));

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        CommonModels models = new CommonModels();
                        models.setImageUrl(jsonObject.getString("thumbnail_url"));
                        models.setTitle(jsonObject.getString("title"));
                        models.setQuality(jsonObject.getString("video_quality"));

                        if (jsonObject.getString("is_tvseries").equals("0")) {
                            models.setVideoType("movie");
                        } else {
                            models.setVideoType("tvseries");
                        }
                        models.setId(jsonObject.getString("videos_id"));
                        //list.add(models);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new ToastMsg(getActivity()).toastIconError(getString(R.string.fetch_error));

            }
        });
        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);

    }
}
