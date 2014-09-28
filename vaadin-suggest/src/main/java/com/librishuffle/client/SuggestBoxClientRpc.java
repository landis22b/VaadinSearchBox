package com.librishuffle.client;

import com.vaadin.shared.communication.ClientRpc;

public interface SuggestBoxClientRpc extends ClientRpc {
  public void showSuggestions(Suggestion[] suggestions);
}