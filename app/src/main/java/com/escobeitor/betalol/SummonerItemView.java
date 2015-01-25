package com.escobeitor.betalol;

import android.content.Context;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.escobeitor.betalol.model.League;
import com.escobeitor.betalol.model.LeagueEntry;
import com.escobeitor.betalol.model.Summoner;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Layout representing an item from the summoner
 * search results
 * Created by escobeitor on 1/21/15.
 */
@EViewGroup(R.layout.extra_summoner_item)
public class SummonerItemView extends LinearLayout implements OnLeagueInfoListener {

    @ViewById(R.id.extra_summoner_item_name)
    TextView name;

    @ViewById(R.id.extra_summoner_item_level)
    TextView level;

    @ViewById(R.id.extra_summoner_item_last_activity)
    TextView lastActivity;

    @ViewById(R.id.extra_summoner_leagues)
    TextView leaguesText;

    public SummonerItemView(Context context) {
        super(context);
    }

    public void bind(Summoner summoner) {
        name.setText(summoner.getName());
        level.setText(getResources().getString(R.string.summoner_item_level, summoner.getSummonerLevel()));

        lastActivity.setText(getResources().getString(R.string.summoner_item_last_activity,
                new SimpleDateFormat("dd/MM/yyyy HH:mm")
                        .format(summoner.getRevisionDate())));

        if(summoner.getLeague() != null) {
            onLeagueInformationAvailable(summoner.getLeague());
        }
    }

    @Background
    @Override
    public void onLeagueInformationAvailable(List<League> leagues) {

        StringBuffer leaguesString = new StringBuffer();
        for(League league : leagues) {

            LeagueEntry entry = league.getEntries().get(0); //The first entries refers to the summoner

            leaguesString.append(league.getQueue()).append(": ")
                    .append(league.getTier()).append(" ")
                    .append(entry.getDivision())
                    .append('\n');
        }

        leaguesString.deleteCharAt(leaguesString.length() - 1);
        updateLeagues(leaguesString.toString());
    }

    @UiThread
    public void updateLeagues(String leagues) {
        leaguesText.setText(leagues);
    }
}
