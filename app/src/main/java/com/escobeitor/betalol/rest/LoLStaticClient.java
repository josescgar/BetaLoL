package com.escobeitor.betalol.rest;

import com.escobeitor.betalol.config.Utils;
import com.escobeitor.betalol.model.Champion;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * League of Lengends static data REST Client interface
 * Created by escobeitor on 1/21/15.
 */
@Rest(rootUrl = "https://global.api.pvp.net/api/lol", converters = {MappingJackson2HttpMessageConverter.class})
public interface LoLStaticClient extends RestClientErrorHandling {

    @Get("/static-data/euw/v1.2/champion/{championId}?api_key=" + Utils.LOL_API_KEY)
    @Accept(MediaType.APPLICATION_JSON)
    public Champion getChampion(int championId);
}
