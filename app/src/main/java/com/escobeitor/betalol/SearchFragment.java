package com.escobeitor.betalol;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.escobeitor.betalol.com.escobeitor.betalol.model.Summoner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

/**
 * Summoner search results fragment
 * Created by escobeitor on 1/20/15.
 */
@EFragment(R.layout.fragment_search)
@OptionsMenu(R.menu.search_menu)
public class SearchFragment extends Fragment {

    /**
     * Search widgets container
     */
    @ViewById(R.id.search_options_container)
    LinearLayout searchContainer;

    /**
     * Listener to be called when a summoner is selected
     */
    private OnSummonerSelectedListener summonerListener = null;

    /**
     * Initialization method (Called at the end of the onCreate method)
     */
    @AfterViews
    public void initialize() {
        this.summonerListener = (OnSummonerSelectedListener) getActivity();
    }

    /**
     * Display the search menu in the fragment
     */
    @OptionsItem(R.id.action_search)
    public void showSearch() {
       if(searchContainer.getVisibility() == View.VISIBLE) {
           searchContainer.setVisibility(View.GONE);
       } else {
           searchContainer.setVisibility(View.VISIBLE);
       }
    }

    /**
     * Interface for fragment interaction with he host
     * activity to notify when a summoner is selected
     */
    public interface OnSummonerSelectedListener {

        public void onSummonerSelected(Summoner summoner);
    }
}
