package com.escobeitor.betalol;


import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.escobeitor.betalol.config.Utils;
import com.escobeitor.betalol.model.League;
import com.escobeitor.betalol.model.Summoner;
import com.escobeitor.betalol.rest.LoLAPIErrorHandler;
import com.escobeitor.betalol.rest.LoLSummonerClient;

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
import java.util.Map.Entry;

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

    @InstanceState
    String searchTermText;

    @RestService
    LoLSummonerClient lolSummonerClient;

    @Bean
    Utils utils;

    @Bean
    LoLAPIErrorHandler apiErrorHandler;

    @Bean
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

        //Restored from the back stack or the background
        if(adapter.summoners != null && !adapter.summoners.isEmpty()) {
            searchHeader.setVisibility(View.GONE);
            searchTerm.setText(searchTermText);
            searchContainer.setVisibility(View.VISIBLE);
        }

        this.summonerListener = (OnSummonerSelectedListener) getActivity();
        dialog = new ProgressDialog(getActivity());
        summonersList.setAdapter(adapter);
    }

    @AfterInject
    public void setErrorHandler() {
        lolSummonerClient.setRestErrorHandler(apiErrorHandler);
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
        searchTermText = searchTerm.getText().toString();
        if(TextUtils.isEmpty(searchTermText)) {
            utils.showToast(errorNoNames);
            return;
        }

        showProgressDialog(true);

        Map<String, Summoner> result = lolSummonerClient.searchSummonersByName(searchTermText, region);
        updateViewWithResults(result);

        showProgressDialog(false);

        getLeagueInformation(result, region);

    }

    /**
     * Search league information for the given summoners
     */
    @Background
    public void getLeagueInformation(Map<String, Summoner> result, String region) {

        if(result == null || result.isEmpty()) {
            return;
        }

        StringBuffer ids = new StringBuffer();
        for(Summoner summoner : result.values()) {
            ids.append(summoner.getId()).append(",");
        }
        ids.deleteCharAt(ids.length() - 1);

        Map<String, List<League>> leagues = lolSummonerClient.getLeaguesForSummoners(ids.toString(), region);
        if(leagues == null || leagues.values().isEmpty()) {
            return;
        }

        //Update views
        for(Entry<String, List<League>> entry : leagues.entrySet()) {
            OnLeagueInfoListener listener = adapter.leagueListeners.get(entry.getKey());
            if(listener == null) {
                continue;
            }

            listener.onLeagueInformationAvailable(entry.getValue());
        }

        //Update summoner model
        for(Summoner summoner : adapter.summoners) {
            List<League> summonerLeagues = leagues.get(Long.toString(summoner.getId()));
            if(summonerLeagues != null) {
                summoner.setLeague(summonerLeagues);
            }
        }
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
            adapter.leagueListeners.clear();
            adapter.notifyDataSetInvalidated();
            return;
        }

        searchHeader.setVisibility(View.GONE);
        adapter.summoners = new ArrayList<>(summoners.values());
        adapter.notifyDataSetChanged();
    }

    public void updateViewWithLeagues(Map<String, List<League>> leagues) {

    }

    @ItemClick(R.id.summoners_list)
    public void summonerClicked(Summoner summoner) {
        summonerListener.onSummonerSelected(
                summoner,
                searchRegion.getSelectedItem().toString().toLowerCase());
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

        public void onSummonerSelected(Summoner summoner, String region);
    }
}
