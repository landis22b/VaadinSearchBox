package com.librishuffle.client;

import com.google.gwt.user.client.ui.SuggestOracle;
import java.io.Serializable;

public class Suggestion implements SuggestOracle.Suggestion, Serializable{
    public Suggestion(){
    }
    
    public Suggestion(String displayString, int itemId){
        this.displayString = displayString;
        this.itemId = itemId;
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
