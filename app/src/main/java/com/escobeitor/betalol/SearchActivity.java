package com.escobeitor.betalol;

import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.escobeitor.betalol.com.escobeitor.betalol.model.Summoner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;

/**
 * Main activity class
 */
@EActivity(R.layout.activity_search)
public class SearchActivity extends ActionBarActivity implements SearchFragment.OnSummonerSelectedListener {

    /**
     * Search fragment
     */
    @InstanceState
    boolean fragmentLoaded = false;

    /**
     * Initialize the activity loading the search fragment
     */
    @AfterViews
    public void initialize() {

        if(!fragmentLoaded) {
            SearchFragment searchFragment = SearchFragment_.builder().build();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, searchFragment)
                    .commit();

            fragmentLoaded = true;
        }
    }

    @Override
    public void onSummonerSelected(Summoner summoner) {
        //TODO
    }
}
