package com.escobeitor.betalol;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.escobeitor.betalol.model.Game;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * List adapter for recent games
 * Created by escobeitor on 1/21/15.
 */
@EBean
public class GameListAdapter extends BaseAdapter implements Serializable {

    public List<Game> games = new ArrayList<Game>();

    @RootContext
    Context context;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GameItemView gameItemView;
        if(convertView == null) {
            gameItemView = GameItemView_.build(context);
        } else {
            gameItemView = (GameItemView) convertView;
        }

        gameItemView.bind(getItem(position));
        return gameItemView;
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Game getItem(int position) {
        return games.get(position);
    }

    @Override
    public long getItemId(int position) {
        return games.get(position).getGameId();
    }
}
