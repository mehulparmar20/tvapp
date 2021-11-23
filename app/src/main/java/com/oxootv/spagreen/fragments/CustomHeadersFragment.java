package com.oxootv.spagreen.fragments;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.app.HeadersSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.OnChildSelectedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.PresenterSelector;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowHeaderPresenter;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.VerticalGridView;

import com.oxootv.spagreen.R;
import com.oxootv.spagreen.ui.IconHeaderItem;
import com.oxootv.spagreen.ui.activity.LeanbackActivity;
import com.oxootv.spagreen.ui.Utils;
import com.oxootv.spagreen.ui.presenter.IconHeaderItemPresenter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

public class CustomHeadersFragment extends HeadersSupportFragment {

    private ArrayObjectAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //customSetBackground(R.color.colorPrimary);
        setOnHeaderViewSelectedListener(new OnHeaderViewSelectedListener() {
            @Override
            public void onHeaderSelected(RowHeaderPresenter.ViewHolder viewHolder, Row row) {
                Object obj = ((ListRow) row).getAdapter().get(0);
                getFragmentManager().beginTransaction().replace(R.id.rows_container, (Fragment) obj).commitAllowingStateLoss();
            }
        });

        setHeaderAdapter();
        customSetBackground(R.color.colorPrimary);
        setCustomPadding();

        VerticalGridView gridView = ((LeanbackActivity) getActivity()).getVerticalGridView(this);
        gridView.setOnChildSelectedListener(new OnChildSelectedListener() {
            @Override
            public void onChildSelected(ViewGroup viewGroup, View view, int i, long l) {
                Object obj = ((ListRow) getAdapter().get(i)).getAdapter().get(0);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.rows_container, (Fragment) obj)
                        .commitAllowingStateLoss();
                ((LeanbackActivity) getActivity()).updateCurrentRowsFragment((Fragment) obj);
            }
        });
    }

    private void setHeaderAdapter() {

        String[] headerItem = new String[]{"Home", "Movies", "TV Series", "Live TV", "Genre", "Country", "Favorite", "My Account"};

        int[] headerIcon = new int[]{R.drawable.outline_home_24, R.drawable.outline_movie_24,
                R.drawable.outline_local_movies_24, R.drawable.outline_live_tv_24, R.drawable.outline_folder_24,
                R.drawable.outline_outlined_flag_24, R.drawable.ic_favorite_border_black_24dp,
                R.drawable.ic_exit_to_app_black_24dp};

        adapter = new ArrayObjectAdapter();

        LinkedHashMap<Integer, Fragment> fragments = ((LeanbackActivity) getActivity()).getFragments();
        ArrayObjectAdapter innerAdapter = null;
        int id = 0;
        for (int i = 0; i < fragments.size(); i++) {
            IconHeaderItem header = new IconHeaderItem(id, headerItem[i], headerIcon[i]);
            //HeaderItem header = new HeaderItem(id, headerItem[i]);
            innerAdapter = new ArrayObjectAdapter();
            innerAdapter.add(fragments.get(i));
            adapter.add(id, new ListRow(header, innerAdapter));
            id++;
        }

        setAdapter(adapter);

        setPresenterSelector(new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object item) {
                return new IconHeaderItemPresenter();
            }
        });

    }

    private void setCustomPadding() {

        getVerticalGridView().setPadding(60, Utils.dpToPx(128, getActivity()), Utils.dpToPx(60, getActivity()), 0);
    }

    private OnItemViewSelectedListener getDefaultItemSelectedListener() {
        return new OnItemViewSelectedListener() {
            @Override
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object o, RowPresenter.ViewHolder rowViewHolder, Row row) {
                Object obj = ((ListRow) row).getAdapter().get(0);
                getFragmentManager().beginTransaction().replace(R.id.rows_container, (Fragment) obj).commit();
            }

            /*@Override
            public void onItemSelected(Object o, Row row) {

            }*/
        };
    }


    private void customSetBackground(int colorResource) {
        try {
            Class clazz = HeadersSupportFragment.class;
            Method m = clazz.getDeclaredMethod("setBackgroundColor", Integer.TYPE);
            m.setAccessible(true);
            m.invoke(this, getResources().getColor(colorResource));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

}
