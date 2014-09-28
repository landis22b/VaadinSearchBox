package com.librishuffle.client;

import com.vaadin.shared.communication.ServerRpc;

public interface SuggestBoxServerRpc extends ServerRpc {
    void getSuggestions(String query);
    void selectionChanged(Suggestion suggestion);
}
