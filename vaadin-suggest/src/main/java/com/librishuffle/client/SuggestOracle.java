package com.librishuffle.client;

import java.util.Arrays;

public class SuggestOracle extends com.google.gwt.user.client.ui.SuggestOracle {

    private final SuggestBoxConnector suggestBoxConnector;

    public SuggestOracle(SuggestBoxConnector suggestBoxConnector){
        this.suggestBoxConnector = suggestBoxConnector;
    }

    @Override
    public void requestSuggestions(final Request request, final Callback callback) {
        final String query = request.getQuery();

        if(!query.equals(suggestBoxConnector.getWidget().getText())) {
            return;
        }

        suggestBoxConnector.register(
                new SuggestBoxClientRpc() {
                    @Override
                    public void showSuggestions(com.librishuffle.client.Suggestion[] suggestions) {
                        suggestBoxConnector.unregister(this);

                        callback.onSuggestionsReady(
                                request,
                                new Response(Arrays.asList(suggestions))
                        );
                    }
                }
        );

        suggestBoxConnector.getRpc().getSuggestions(request.getQuery());
    }
}
