package com.librishuffle;

import com.librishuffle.client.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link SuggestBox} is a text box which displays a
 * set of selections that match the user's input.
 *
 * <pre>
 *   SuggestBox suggestBox = new SuggestBox(){
 *        @Override
 *        public Suggestion[] getItemSuggestions(String query) {
 *              new Suggestion("Cat", 1),
 *              new Suggestion("Dog", 2),
 *              new Suggestion("Horse", 3),
 *              new Suggestion("Carnary", 4)
 *        }
 *   };
 *
 * </pre>
 *
 * Using the example above, if the user types "C" into the text widget,
 * the SuggestBox will display "Cat" and "Canary" suggestions.
 */
public abstract class SuggestBox extends com.vaadin.ui.AbstractComponent{

    public SuggestBox() {
        registerRpc(new SuggestBoxServerRpcImpl());
    }

    public abstract Suggestion[] getItemSuggestions(String query);

    private final List<SelectionChangedHandler> selectionChangedHandlers = new ArrayList<SelectionChangedHandler>();

    public void addSelectionChangedListener(SelectionChangedHandler handler){
        selectionChangedHandlers.add(handler);
    }

    private class SuggestBoxServerRpcImpl implements SuggestBoxServerRpc {

        private final SuggestBoxClientRpc clientRpc = getRpcProxy(SuggestBoxClientRpc.class);

        @Override
        public void getSuggestions(String query) {
            Suggestion[] suggestions = getItemSuggestions(query);

            if(suggestions == null || suggestions.length == 0){
                return;
            }

            clientRpc.showSuggestions(suggestions);
        }

        @Override
        public void selectionChanged(Suggestion suggestion) {
            for(SelectionChangedHandler handler: selectionChangedHandlers){
                handler.onSelectionChanged(suggestion);
            }
        }
    }
}
