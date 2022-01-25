package com.yahumott.tvapp.ui.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.widget.Presenter;

import com.yahumott.tvapp.R;
import com.yahumott.tvapp.model.MovieSingleDetails;
import com.yahumott.tvapp.ui.VideoDetailsViewHolder;

public class MovieDetailsDescriptionPresenter extends Presenter {

    Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_movie_details, parent, false);
        return new VideoDetailsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {

        MovieSingleDetails singleDetails = (MovieSingleDetails) item;
        VideoDetailsViewHolder holder = (VideoDetailsViewHolder) viewHolder;
        holder.bind(singleDetails, context);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }
}
