package com.librishuffle.client;

import com.librishuffle.shared.SuggestionDto;
import com.vaadin.shared.communication.ClientRpc;

public interface SuggestBoxClientRpc extends ClientRpc {
  void showSuggestions(String originalQuery, SuggestionDto[] suggestions);
}