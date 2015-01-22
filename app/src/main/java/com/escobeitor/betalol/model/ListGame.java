package com.escobeitor.betalol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Wrapper for mapping a list of games from the API
 * Created by escobeitor on 1/21/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListGame implements Serializable {

    private List<Game> games;

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
