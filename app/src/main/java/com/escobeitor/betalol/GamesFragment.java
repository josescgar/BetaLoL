package com.escobeitor.betalol;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.escobeitor.betalol.config.Utils;
import com.escobeitor.betalol.model.Game;
import com.escobeitor.betalol.model.ListGame;
import com.escobeitor.betalol.model.Summoner;
import com.escobeitor.betalol.rest.LoLAPIErrorHandler;
import com.escobeitor.betalol.rest.LoLSummonerClient;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;


/**
 * Recent summoner games fragment
 * Created by escobeitor on 1/21/15.
 */
@EFragment(R.layout.fragment_games)
public class GamesFragment extends Fragment {

    @InstanceState
    @FragmentArg
    String region;

    @InstanceState
    @FragmentArg
    Summoner summoner;

    @ViewById(R.id.games_header)
    TextView listHeader;

    @ViewById(R.id.games_list)
    ListView gamesList;

    @ViewById(R.id.games_no_results)
    TextView noResults;

    @RestService
    LoLSummonerClient summonerClient;

    @Bean
    LoLAPIErrorHandler apiErrorHandler;

    @Bean
    GameListAdapter adapter;

    private ProgressDialog dialog;

    @AfterInject
    public void setErrorHandler() {
        summonerClient.setRestErrorHandler(apiErrorHandler);
    }

    @AfterViews
    public void initialize() {

        listHeader.setText(getString(R.string.games_header, summoner.getName()));
        gamesList.setAdapter(adapter);
        dialog = new ProgressDialog(getActivity());
        if(adapter.games == null || adapter.games.isEmpty()) {
            loadGames();
        }
    }

    @Background
    public void loadGames() {

        showProgressDialog(true);

        ListGame games = summonerClient.getRecentGamesForSummoner(summoner.getId(), region);
        updateGamesList(games);

        showProgressDialog(false);

    }

    @UiThread
    public void updateGamesList(ListGame games) {

        if(games == null || games.getGames() == null || games.getGames().isEmpty()) {
            noResults.setVisibility(View.VISIBLE);
            adapter.games.clear();
            adapter.notifyDataSetChanged();
        }

        adapter.games = games.getGames();
        adapter.notifyDataSetChanged();

    }

    @UiThread
    public void showProgressDialog(boolean show) {
        if(show) {
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }
}
