package com.escobeitor.betalol;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.escobeitor.betalol.model.Summoner;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom adapter for the summoner list
 * Created by escobeitor on 1/21/15.
 */
@EBean
public class SummonerListAdapter extends BaseAdapter implements Serializable {

    List<Summoner> summoners = new ArrayList<Summoner>();

    @RootContext
    Context context;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SummonerItemView summonerItemView;
        if(convertView == null) {
            summonerItemView = SummonerItemView_.build(context);
        } else {
            summonerItemView = (SummonerItemView) convertView;
        }

        summonerItemView.bind(summoners.get(position));

        return summonerItemView;
    }

    @Override
    public int getCount() {
        return summoners.size();
    }

    @Override
    public Summoner getItem(int position) {
        return summoners.get(position);
    }

    @Override
    public long getItemId(int position) {
        return summoners.get(position).getId();
    }
}
