package com.escobeitor.betalol.rest;


import com.escobeitor.betalol.config.Utils;
import com.escobeitor.betalol.model.ListGame;
import com.escobeitor.betalol.model.Summoner;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Map;

/**
 * League Of Legends API REST Client interface for regional servers
 * @link https://developer.riotgames.com/api/methods
 * Created by escobeitor on 1/21/15.
 */
@Rest(rootUrl = "https://euw.api.pvp.net/api/lol", converters = {MappingJackson2HttpMessageConverter.class})
public interface LoLSummonerClient extends RestClientErrorHandling {

    /**
     * Searches the given list of summoner names in the LoL database for the given region
     * @param names List of summoner names to search separated by commas
     * @param region LoL server region
     * @return
     */
    @Get("/{region}/v1.4/summoner/by-name/{names}?api_key=" + Utils.LOL_API_KEY)
    @Accept(MediaType.APPLICATION_JSON)
    public Map<String, Summoner> searchSummonersByName(String names, String region);

    /**
     * Returns a list of recent games played by the given summoner
     * @param summonerId Summoner ID
     * @param region LoL server region
     * @return
     */
    @Get("/{region}/v1.3/game/by-summoner/{summonerId}/recent?api_key=" + Utils.LOL_API_KEY)
    @Accept(MediaType.APPLICATION_JSON)
    public ListGame getRecentGamesForSummoner(long summonerId, String region);


}
