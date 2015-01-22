package com.escobeitor.betalol;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.escobeitor.betalol.model.Summoner;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;

/**
 * Layout representing an item from the summoner
 * search results
 * Created by escobeitor on 1/21/15.
 */
@EViewGroup(R.layout.extra_summoner_item)
public class SummonerItemView extends LinearLayout {

    @ViewById(R.id.extra_summoner_item_name)
    TextView name;

    @ViewById(R.id.extra_summoner_item_level)
    TextView level;

    @ViewById(R.id.extra_summoner_item_last_activity)
    TextView lastActivity;

    public SummonerItemView(Context context) {
        super(context);
    }

    public void bind(Summoner summoner) {
        name.setText(summoner.getName());
        level.setText(getResources().getString(R.string.summoner_item_level, summoner.getSummonerLevel()));

        lastActivity.setText(getResources().getString(R.string.summoner_item_last_activity,
                new SimpleDateFormat("dd/MM/yyyy HH:mm")
                        .format(summoner.getRevisionDate())));
    }
}
