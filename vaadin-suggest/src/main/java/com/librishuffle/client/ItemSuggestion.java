package com.librishuffle.client;

import com.google.gwt.user.client.ui.SuggestOracle;

import com.librishuffle.shared.SuggestionDto;

import java.io.Serializable;

public class ItemSuggestion implements SuggestOracle.Suggestion, Serializable{
    public ItemSuggestion(){
    }
    
    public ItemSuggestion(SuggestionDto suggestionDto){
        this.displayString = suggestionDto.getDisplayString();
        this.itemId = suggestionDto.getItemId();
    }
    
    private String displayString;
    private int itemId;
    
    @Override
    public String getDisplayString() {
        return displayString;
    }

    public int getItemId(){
        return itemId;
    }

    @Override
    public String getReplacementString() {
        return displayString;
    }
 
    public void setDisplayString(String displayString){
        this.displayString = displayString;
    }

    public void setItemId(int itemId){
        this.itemId = itemId;
    }
}
