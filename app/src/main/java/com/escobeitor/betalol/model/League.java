package com.escobeitor.betalol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Represents information for all leagues
 * where that the summoner has
 * Created by escobeitor on 1/25/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class League {

    private List<LeagueEntry> entries;

    private String queue;

    private String tier;

    public List<LeagueEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<LeagueEntry> entries) {
        this.entries = entries;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }
}
