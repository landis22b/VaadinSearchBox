package com.librishuffle.client;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gwt.user.client.Timer;

import com.librishuffle.shared.SuggestionDto;

import java.util.HashSet;
import java.util.Set;

import static java.util.concurrent.TimeUnit.SECONDS;

public class SuggestOracle extends com.google.gwt.user.client.ui.SuggestOracle implements SuggestBoxClientRpc {

    private final SuggestBoxConnector suggestBoxConnector;

    public SuggestOracle(SuggestBoxConnector suggestBoxConnector){
        this.suggestBoxConnector = suggestBoxConnector;
        suggestBoxConnector.register(this);
    }

    private static class CallbackAndRequest {
        private Callback callback;
        private Request request;

        private CallbackAndRequest(Callback callback, Request request) {
            this.callback = callback;
            this.request = request;
        }

        public Callback getCallback() {
            return callback;
        }

        public Request getRequest() {
            return request;
        }
    }

    private final Cache<String, CallbackAndRequest> callbackCache = CacheBuilder
                                                                    .newBuilder()
                                                                    .expireAfterWrite(15, SECONDS)
                                                                    .build();

    @Override
    public void requestSuggestions(final Request request, final Callback callback) {
        final String query = request.getQuery();

        if(query.length() < suggestBoxConnector.getState().queryMinLength){
            return;
        }

        if(!query.equals(suggestBoxConnector.getWidget().getText())) {
            return;
        }

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                callbackCache.put(query, new CallbackAndRequest(callback, request));
                suggestBoxConnector.getRpc().suggestFor(query);
            }
        };

        int delayMilis = suggestBoxConnector.getState().delayMilis;

        if(delayMilis > 0){
            new Timer() {
                @Override
                public void run() {
                    if(query.equals(suggestBoxConnector.getWidget().getText())) {
                        runnable.run();
                    }
                }
            }.schedule(delayMilis);
        } else {
            runnable.run();
        }
    }

    @Override
    public void showSuggestions(String originalQuery, SuggestionDto[] suggestionDtos) {
        CallbackAndRequest callback = callbackCache.getIfPresent(originalQuery);

        if(callback == null){
            return;
        }

        Set<Suggestion> suggestions = new HashSet<Suggestion>(suggestionDtos.length);

        for (SuggestionDto suggestionDto : suggestionDtos) {
            suggestions.add(new ItemSuggestion(suggestionDto));
        }

        callback.getCallback().onSuggestionsReady(callback.getRequest(), new Response(suggestions));
    }
}
