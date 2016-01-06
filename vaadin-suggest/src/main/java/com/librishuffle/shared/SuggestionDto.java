package com.librishuffle.shared;

import java.io.Serializable;

public class SuggestionDto implements Serializable {

    private int itemId;
    private String displayString;

    public SuggestionDto() {
    }

    public SuggestionDto(int itemId, String displayString) {
        this.itemId = itemId;
        this.displayString = displayString;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getDisplayString() {
        return displayString;
    }

    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }
}
