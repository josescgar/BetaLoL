package com.escobeitor.betalol;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.escobeitor.betalol.config.Utils;
import com.escobeitor.betalol.model.Champion;
import com.escobeitor.betalol.model.DBChampionHelper;
import com.escobeitor.betalol.model.Game;
import com.escobeitor.betalol.rest.LoLStaticClient;
import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.rest.RestService;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * Custom list view representing a game
 * Created by escobeitor on 1/21/15.
 */
@EViewGroup(R.layout.extra_game_item)
public class GameItemView extends LinearLayout {

    @ViewById(R.id.item_game_champion)
    TextView champName;

    @ViewById(R.id.item_game_win)
    TextView gameResult;

    @ViewById(R.id.item_game_team)
    TextView team;

    @ViewById(R.id.item_game_kills)
    TextView kills;

    @ViewById(R.id.item_game_type)
    TextView type;

    @ViewById(R.id.item_game_time)
    TextView timing;

    @OrmLiteDao(helper = DBChampionHelper.class, model = Champion.class)
    Dao<Champion, Long> championDao;

    @Bean
    Utils utils;

    @RestService
    LoLStaticClient lolStaticClient;

    @StringRes(R.string.error_no_champion)
    String noChampError;

    @StringRes(R.string.champion_loading)
    String champDefault;

    public GameItemView(Context context) {
        super(context);
    }

    public void bind(Game game) {

        champName.setText(champDefault);
        setChampionName(game);

        if(game.getStats().isWin()) {
            gameResult.setText(R.string.item_game_won);
            gameResult.setTextColor(Color.GREEN);
        } else {
            gameResult.setText(R.string.item_game_lost);
            gameResult.setTextColor(Color.RED);
        }

        switch (game.getTeamId()) {
            case Game.TEAM_BLUE:
                team.setText(R.string.item_game_blue);
                team.setTextColor(Color.BLUE);
                break;
            case Game.TEAM_PURPLE:
                team.setText(R.string.item_game_purple);
                team.setTextColor(Color.MAGENTA);
                break;
        }

        kills.setText(getResources().getString(R.string.item_game_kills,
                game.getStats().getChampionsKilled(),
                game.getStats().getNumDeaths(),
                game.getStats().getAssists(),
                game.getIpEarned()));

        type.setText(getResources().getString(R.string.item_game_type,
                game.getGameType(),
                game.getSubType()));

        timing.setText(getResources().getString(R.string.item_game_time,
                (int) Math.ceil(game.getStats().getTimePlayed() / 60),
                new SimpleDateFormat("dd/MM/yyyy HH:mm").format(game.getCreateDate())));
    }

    @Background
    public void setChampionName(Game game) {

        Champion champ = game.getChampion();

        //Try to find it in the local database
        if(champ == null) {

            try {
                champ = championDao.queryForId((long) game.getChampionId());
            } catch(SQLException e) {
                utils.showToast(e.getMessage());
                champ = null;
            }
        }

        //Fetch it from the API
        if(champ == null) {
            try {

                champ = lolStaticClient.getChampion(game.getChampionId());

                //Store in local database for future reference
                if(champ != null && championDao.isTableExists()) {
                    championDao.createOrUpdate(champ);
                }

            } catch (Exception e) {
                utils.showToast(e.getMessage());
            }
        }

        //Change the name
        updateChampionName(champ);
    }

    @UiThread
    public void updateChampionName(Champion champion) {

        if(champion == null) {
            utils.showToast(noChampError);
            return;
        }

        champName.setText(champion.getName());
    }
}
