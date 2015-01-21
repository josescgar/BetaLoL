package com.escobeitor.betalol.rest;

import com.escobeitor.betalol.config.Utils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.core.NestedRuntimeException;

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

        utils.showToast(e.getMessage());

    }
}
