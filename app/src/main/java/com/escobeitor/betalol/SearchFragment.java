package com.escobeitor.betalol;


import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.escobeitor.betalol.config.Utils;
import com.escobeitor.betalol.model.Summoner;
import com.escobeitor.betalol.rest.LoLAPIErrorHandler;
import com.escobeitor.betalol.rest.LoLRESTClient;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Summoner search results fragment
 * Created by escobeitor on 1/20/15.
 */
@EFragment(R.layout.fragment_search)
@OptionsMenu(R.menu.search_menu)
public class SearchFragment extends Fragment {

    @ViewById(R.id.search_options_container)
    LinearLayout searchContainer;

    @ViewById(R.id.search_region)
    Spinner searchRegion;

    @ViewById(R.id.search_term)
    EditText searchTerm;

    @ViewById(R.id.summoners_search_header)
    TextView searchHeader;

    @ViewById(R.id.summoners_list)
    ListView summonersList;

    @StringRes(R.string.error_no_names)
    String errorNoNames;

    @RestService
    LoLRESTClient lolRestClient;

    @Bean
    Utils utils;

    @Bean
    LoLAPIErrorHandler apiErrorHandler;

    @Bean
    @InstanceState
    SummonerListAdapter adapter;

    private ProgressDialog dialog;

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
        dialog = new ProgressDialog(getActivity());
        summonersList.setAdapter(adapter);
    }

    @AfterInject
    public void setErrorHandler() {
        lolRestClient.setRestErrorHandler(apiErrorHandler);
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
     * Perform the search
     */
    @Click(R.id.search_button)
    @Background
    public void doSearchSummoners() {

        final String region = searchRegion.getSelectedItem().toString().toLowerCase();
        final String names = searchTerm.getText().toString();
        if(TextUtils.isEmpty(names)) {
            utils.showToast(errorNoNames);
            return;
        }

        showProgressDialog(true);

        Map<String, Summoner> result = lolRestClient.searchSummonersByName(names, region);
        updateViewWithResults(result);

        showProgressDialog(false);
    }

    /**
     * Update the results list with found summoners
     * @param summoners
     */
    @UiThread
    public void updateViewWithResults(Map<String, Summoner> summoners) {

        if(summoners == null || summoners.isEmpty()) {
            searchHeader.setVisibility(View.VISIBLE);
            adapter.summoners.clear();
            adapter.notifyDataSetInvalidated();
            return;
        }

        searchHeader.setVisibility(View.GONE);

        adapter.summoners = new ArrayList<>(summoners.values());
        adapter.notifyDataSetChanged();
    }

    @ItemClick(R.id.summoners_list)
    public void summonerClicked(Summoner summoner) {
        summonerListener.onSummonerSelected(summoner);
    }

    @UiThread
    public void showProgressDialog(boolean show) {
        if(show) {
            dialog.show();
        } else {
            dialog.dismiss();
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
