package com.librishuffle.client;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

@Connect(com.librishuffle.SuggestBox.class)
public class SuggestBoxConnector extends AbstractComponentConnector{

    private final SuggestBoxServerRpc rpc = RpcProxy.create(SuggestBoxServerRpc.class, this);

    @Override
    protected Widget createWidget(){
        final SuggestOracle oracle = new com.librishuffle.client.SuggestOracle(this);

        com.google.gwt.user.client.ui.SuggestBox searchBoxWidget = new com.google.gwt.user.client.ui.SuggestBox(oracle);

        searchBoxWidget.addSelectionHandler(
                new SelectionHandler<SuggestOracle.Suggestion>() {
                    @Override
                    public void onSelection(SelectionEvent<SuggestOracle.Suggestion> event) {
                        Suggestion suggestion = (Suggestion)event.getSelectedItem();

                        if(suggestion == null) {
                            return;
                        }

                        getRpc().selectionChanged(suggestion);
                    }
                }
        );

        return searchBoxWidget;
    }

    @Override
    public com.google.gwt.user.client.ui.SuggestBox getWidget(){
        return (com.google.gwt.user.client.ui.SuggestBox) super.getWidget();
    }

    @Override
    public SuggestBoxState getState() {
        return (SuggestBoxState) super.getState();
    }

    public void register(SuggestBoxClientRpc suggestBoxClientRpc){
        registerRpc(SuggestBoxClientRpc.class, suggestBoxClientRpc);
    }

    public void unregister(SuggestBoxClientRpc suggestBoxClientRpc){
        unregisterRpc(SuggestBoxClientRpc.class, suggestBoxClientRpc);
    }

    public SuggestBoxServerRpc getRpc() {
        return rpc;
    }
}
