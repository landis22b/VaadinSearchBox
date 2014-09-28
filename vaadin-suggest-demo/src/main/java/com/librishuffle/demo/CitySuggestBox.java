package com.librishuffle.demo;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.librishuffle.SuggestBox;
import com.librishuffle.client.SelectionChangedHandler;
import com.librishuffle.client.Suggestion;
import com.vaadin.ui.Notification;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Bernd Hopp
 * @version 1.0
 * CitySuggestBox is a sample SuggestBox that will suggest cities on user input
 * */
public class CitySuggestBox extends SuggestBox{

    //this is the list of cities that a user can choose from
    private final List<Suggestion> CITY_SUGGESTIONS = ImmutableList.of(
            new Suggestion("Berlin", 1),
            new Suggestion("Bochum", 5),
            new Suggestion("Hamburg", 2),
            new Suggestion("München", 3),
            new Suggestion("Köln", 4)
    );

    public CitySuggestBox(){

        //show user selections in a notification-popup
        addSelectionChangedListener(
                new SelectionChangedHandler() {
                    @Override
                    public void onSelectionChanged(Suggestion suggestion) {
                        Notification.show(suggestion.getDisplayString() + " selected");
                    }
                }
        );
    }

    @Override
    public Suggestion[] getItemSuggestions(String query) {

        //new variable with removed whitespace at start or end of query
        final String queryTrimmed = query.trim();

        Predicate<Suggestion> matchesQueryPredicate = new Predicate<Suggestion>() {
            @Override
            public boolean apply(Suggestion suggestion) {
                return StringUtils.startsWithIgnoreCase(suggestion.getDisplayString(), queryTrimmed);
            }
        };

        //filter the available cities by name matching the query, limit to 5 matches and return it as an array
        return FluentIterable.from(CITY_SUGGESTIONS)
                .filter(matchesQueryPredicate)
                .toArray(Suggestion.class);
    }
}
