package com.yahumott.tvapp.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.leanback.app.RowsSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

import com.yahumott.tvapp.Config;
import com.yahumott.tvapp.Constants;
import com.yahumott.tvapp.NetworkInst;
import com.yahumott.tvapp.network.api.HomeContentList;
import com.yahumott.tvapp.ui.activity.PlayerActivity;
import com.yahumott.tvapp.utils.PreferenceUtils;
import com.yahumott.tvapp.R;
import com.yahumott.tvapp.database.DatabaseHelper;
import com.yahumott.tvapp.model.Channel;
import com.yahumott.tvapp.model.HomeContent;
import com.yahumott.tvapp.model.VideoContent;
import com.yahumott.tvapp.network.RetrofitClient;
import com.yahumott.tvapp.network.api.HomeApi;
import com.yahumott.tvapp.ui.BackgroundHelper;
import com.yahumott.tvapp.ui.activity.ErrorActivity;
import com.yahumott.tvapp.ui.activity.LeanbackActivity;
import com.yahumott.tvapp.ui.activity.VideoDetailsActivity;
import com.yahumott.tvapp.ui.presenter.CardPresenter;
import com.yahumott.tvapp.ui.presenter.SliderCardPresenter;
import com.yahumott.tvapp.ui.presenter.TvPresenter;
import com.yahumott.tvapp.utils.PaidDialog;
import com.yahumott.tvapp.video_service.PlaybackModel;
import com.yahumott.tvapp.video_service.VideoPlaybackActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends RowsSupportFragment {

    private BackgroundHelper bgHelper;
    private ArrayObjectAdapter rowsAdapter;
    private CardPresenter cardPresenter;
    private View v;
    private List<HomeContent> homeContents = new ArrayList<>();
    private LeanbackActivity activity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bgHelper = new BackgroundHelper(getActivity());
        activity = (LeanbackActivity) getActivity();
        activity.showLogo();


        setOnItemViewClickedListener(getDefaultItemViewClickedListener());
        setOnItemViewSelectedListener(getDefaultItemSelectedListener());


        if (new NetworkInst(activity).isNetworkAvailable()) {
            loadHomeContent();
        } else {
            Intent intent = new Intent(activity, ErrorActivity.class);
            startActivity(intent);
            activity.finish();
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = super.onCreateView(inflater, container, savedInstanceState);
        return v;
    }


    public void loadHomeContent() {

        final SpinnerFragment mSpinnerFragment = new SpinnerFragment();
        final FragmentManager fm = getFragmentManager();
        fm.beginTransaction().add(R.id.custom_frame_layout, mSpinnerFragment).commit();

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        HomeApi api = retrofit.create(HomeApi.class);
        Call<List<HomeContent>> call = api.getHomeContent(Config.API_KEY);
        call.enqueue(new Callback<List<HomeContent>>() {
            @Override
            public void onResponse(Call<List<HomeContent>> call, Response<List<HomeContent>> response) {


                if (response.isSuccessful()) {
                    List<HomeContent> homeContents = response.body();
                    //Log.d("size:", homeContents.size()+"");

                    if (homeContents.size() > 0) {
                        loadRows(homeContents);

                        //save latest movies in constant file for temporary
                        // to add/update channel
                        if (homeContents.get(2).getContent() != null) {
                            Constants.movieList.clear();
                            Constants.movieList = homeContents.get(2).getContent();
                        }
                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }

                // hide the spinner
                fm.beginTransaction().remove(mSpinnerFragment).commitAllowingStateLoss();

            }

            @Override
            public void onFailure(Call<List<HomeContent>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the spinner
                getFragmentManager().beginTransaction().remove(mSpinnerFragment).commit();
            }
        });


    }
    private void loadRows(List<HomeContent> homeContents) {
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        cardPresenter = new CardPresenter();
        SliderCardPresenter sliderCardPresenter = new SliderCardPresenter();
        TvPresenter tvPresenter = new TvPresenter();

        int i;
        for (i = 0; i < homeContents.size(); i++) {
            ArrayObjectAdapter listRowAdapter;
            HeaderItem header;
            if (i == 0) {
                // load slider
                listRowAdapter = new ArrayObjectAdapter(sliderCardPresenter);
                header = new HeaderItem(i, "");
            } else if (i == 1) {
                //load tv layout
                listRowAdapter = new ArrayObjectAdapter(tvPresenter);
                header = new HeaderItem(i, homeContents.get(i).getTitle());
            } else {
                listRowAdapter = new ArrayObjectAdapter(cardPresenter);
                header = new HeaderItem(i, homeContents.get(i).getTitle());
            }
            //for (int j = 0; j < NUM_COLS; j++) {
            Log.d("row", homeContents.get(i).getContent().toString());
            for (int j = 0; j < homeContents.get(i).getContent().size(); j++) {

                VideoContent videoContent = homeContents.get(i).getContent().get(j);

                if (homeContents.get(i).getType().equalsIgnoreCase("tv")) {
                    videoContent.setType("tv");
                } else if (homeContents.get(i).getType().equalsIgnoreCase("movie")) {
                    videoContent.setType("movie");
                } else if (homeContents.get(i).getType().equalsIgnoreCase("tvseries")) {
                    videoContent.setType("tvseries");
                }
                else if(homeContents.get(i).getType().equalsIgnoreCase("genre")) {
                    videoContent.setType("genres");
                }
                else if (homeContents.get(i).getType().equalsIgnoreCase("slider")) {
//                    if (videoContent.getIsTvseries()!="null") {
//                        videoContent.setType("tvseries");
//                    } else if (videoContent.getIsTvseries()=="null") {
//                        videoContent.setType("movie");
                    videoContent.setType("slider");
//                    }
                }

                listRowAdapter.add(videoContent);
            }
            rowsAdapter.add(new ListRow(header, listRowAdapter));
        }

        setAdapter(rowsAdapter);

        setCustomPadding();

    }

    private void setCustomPadding() {
//        getView().setPadding(Utils.dpToPx(-24, getContext()), Utils.dpToPx(70, getContext()), 0, 0);
    }

    // click listener
    private OnItemViewClickedListener getDefaultItemViewClickedListener() {
        return new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder viewHolder, Object o,
                                      RowPresenter.ViewHolder viewHolder2, Row row) {

                VideoContent videoContent = (VideoContent) o;

                String status = new DatabaseHelper(getContext()).getActiveStatusData().getStatus();

//                if (videoContent.getType().equals("tvseries")) {
////                    if (videoContent.gets().equals("1")) {
//                        if (PreferenceUtils.isValid(getActivity())) {
//                            if (status.equals("active")) {
//                                PlaybackModel model = new PlaybackModel();
//                                model.setId(Long.parseLong(videoContent.getId()));
//                                model.setTitle(videoContent.getTitle());
//                                model.setDescription(videoContent.getDescription());
//                                model.setVideoType(videoContent.getStreamFrom());
//                                model.setCategory("tv");
//                                model.setVideoUrl(videoContent.getStreamUrl());
//                                model.setCardImageUrl(videoContent.getPosterUrl());
//                                model.setBgImageUrl(videoContent.getThumbnailUrl());
//
//
//                                Intent intent = new Intent(getActivity(), PlayerActivity.class);
//                                intent.putExtra(VideoPlaybackActivity.EXTRA_VIDEO, model);
//                                startActivity(intent);
//                            } else {
//                                //subscription is not active
//                                //new PaidDialog(getActivity()).showPaidContentAlertDialog();
//                                PaidDialog dialog = new PaidDialog(getContext());
//                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//                                dialog.show();
//                            }
//                        } else {
//                            //saved data is not valid, because it was saved more than 2 hours ago
//                            PreferenceUtils.updateSubscriptionStatus(getActivity());
//                            PaidDialog dialog = new PaidDialog(getContext());
//                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//                            dialog.show();
//                        }
////                    } else {
////                        PlaybackModel model = new PlaybackModel();
////                        model.setId(Long.parseLong(videoContent.getId()));
////                        model.setTitle(videoContent.getTitle());
////                        model.setDescription(videoContent.getDescription());
////                        model.setVideoType(videoContent.getStreamFrom());
////                        model.setCategory("tv");
////                        model.setVideoUrl(videoContent.getStreamUrl());
////                        model.setCardImageUrl(videoContent.getPosterUrl());
////                        model.setBgImageUrl(videoContent.getThumbnailUrl());
////
////
////                        Intent intent = new Intent(getActivity(), PlayerActivity.class);
////                        intent.putExtra(VideoPlaybackActivity.EXTRA_VIDEO, model);
////                        startActivity(intent);
////                    }
//
//                } else {
                if(videoContent.getType()!="slider")
                {
                    Intent intent = new Intent(getActivity(), VideoDetailsActivity.class);
                    intent.putExtra("id", videoContent.getId());
                    intent.putExtra("type", videoContent.getType());
                    intent.putExtra("thumbImage", videoContent.getThumbnailUrl());
                    Log.d("video id", videoContent.getId());
                    Log.d("video type", videoContent.getType());
                    Log.d("video thumbImage", videoContent.getThumbnailUrl());
                    //poster transition
                    ImageView imageView = ((ImageCardView) viewHolder.view).getMainImageView();
                    Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            imageView, VideoDetailsFragment.TRANSITION_NAME).toBundle();

                    startActivity(intent, bundle);
                }

//                }


            }
        };
    }


    //listener for setting blur background each time when the item will select.
    protected OnItemViewSelectedListener getDefaultItemSelectedListener() {
        return new OnItemViewSelectedListener() {
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, final Object item,
                                       RowPresenter.ViewHolder rowViewHolder, Row row) {

                if (item instanceof VideoContent) {
                    bgHelper = new BackgroundHelper(getActivity());
                    bgHelper.prepareBackgroundManager();
                    bgHelper.startBackgroundTimer(((VideoContent) item).getPosterUrl());
                } else if (item instanceof Channel) {

                    bgHelper = new BackgroundHelper(getActivity());
                    bgHelper.prepareBackgroundManager();
                    bgHelper.startBackgroundTimer(((Channel) item).getPosterUrl());

                }

            }
        };
    }

}
