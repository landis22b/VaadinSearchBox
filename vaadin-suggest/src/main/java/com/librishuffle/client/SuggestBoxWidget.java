package com.librishuffle.client;

import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;

public class SuggestBoxWidget extends SuggestBox {
	public SuggestBoxWidget(SuggestOracle oracle) {
        super(oracle);
    }
}