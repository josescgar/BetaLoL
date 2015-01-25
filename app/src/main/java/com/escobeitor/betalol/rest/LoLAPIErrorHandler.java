package com.escobeitor.betalol.rest;

import com.escobeitor.betalol.config.Utils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

/**
 * League of Lengends error handler
 * Created by escobeitor on 1/21/15.
 */
@EBean
public class LoLAPIErrorHandler implements RestErrorHandler {

    @Bean
    Utils utils;

    @Override
    public void onRestClientExceptionThrown(NestedRuntimeException e) {

        /**
         * In the LOL API a 404 result code means that no data
         * was found for the given query, so it's not a real error
         */
        if(e instanceof HttpClientErrorException
                && ((HttpClientErrorException) e).getStatusCode() == HttpStatus.NOT_FOUND) {
            return;
        }

        utils.showToast(e.getMessage());

    }
}
