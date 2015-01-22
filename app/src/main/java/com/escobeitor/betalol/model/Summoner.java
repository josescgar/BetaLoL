package com.escobeitor.betalol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a LoL summoner
 * Created by escobeitor on 1/20/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Summoner implements Serializable {

    /**
     * Summoner unique ID
     */
    private long id;

    /**
     * Summoner name
     */
    private String name;

    /**
     * Summoner profile icon ID
     */
    private int profileIconId;

    /**
     * Last activity of the summoner
     */
    private Date revisionDate;

    /**
     * Summoner level
     */
    private int summonerLevel;

    public int getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(int summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }

    public int getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(int profileIconId) {
        this.profileIconId = profileIconId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
