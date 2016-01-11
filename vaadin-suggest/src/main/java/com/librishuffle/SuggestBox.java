package com.librishuffle;

import com.librishuffle.client.SuggestBoxClientRpc;
import com.librishuffle.client.SuggestBoxServerRpc;
import com.librishuffle.client.SuggestBoxState;
import com.librishuffle.shared.SuggestionDto;

import java.util.Set;

import static com.google.gwt.thirdparty.guava.common.base.Preconditions.checkArgument;
import static com.google.gwt.thirdparty.guava.common.base.Strings.isNullOrEmpty;

/**
 * A {@link SuggestBox} is a text box which displays a
 * set of selections that match the user's input.
 *
 * <pre>
 *   SuggestBox suggestBox = new SuggestBox(){
 *        {@literal @}Override
 *        public Set{@literal <}SuggestionDto{@literal >} getItemSuggestions(String query) {
 *            return ImmutableSet.of(
 *                new SuggestionDto(1, "Cat"),
 *                new SuggestionDto(2, "Dog"),
 *                new SuggestionDto(3, "Horse"),
 *                new SuggestionDto(4, "Carnary")
 *            );
 *        }
 *
 *        {@literal @}Override
 *        public void selectionChanged(int itemId) {
 *          //handle selection here
 *        };
 * </pre>
 *
 * Using the example above, if the user types anything into the text widget,
 * the SuggestBox will display "Cat", "Dog", "Horse" and "Canary" suggestions.
 * Filter logic needs to be implemented here usually.
 */
public abstract class SuggestBox extends com.vaadin.ui.AbstractComponent implements SuggestBoxServerRpc{

    public SuggestBox(){
        this("type query here", 800, 4, true);
    }

    /**
     @param placeHolderText a text to be displayed when the box is empty, usually something like 'type your query here'
     @param delayMilis the miliseconds to wait for the user to type until a request for suggestions is send to the server
     @param queryMinLength the minimum length of a query to have to request suggestions
      * */
    public SuggestBox(String placeHolderText, int delayMilis, int queryMinLength) {
        this(placeHolderText, delayMilis, queryMinLength, true);
    }

    /**
     @param placeHolderText a text to be displayed when the box is empty, usually something like 'type your query here'
     @param delayMilis the miliseconds to wait for the user to type until a request for suggestions is send to the server
     @param queryMinLength the minimum length of a query to have to request suggestions
     @param clearInputAfterSelection set to true if the box should be empty after an suggestion was selected
    * */
    public SuggestBox(String placeHolderText, int delayMilis, int queryMinLength, boolean clearInputAfterSelection) {
        checkArgument(!isNullOrEmpty(placeHolderText));
        checkArgument(delayMilis >= 0);
        checkArgument(queryMinLength >= 1);

        registerRpc(this);
        SuggestBoxState state = getState();
        state.placeHolderText = placeHolderText;
        state.delayMilis = delayMilis;
        state.queryMinLength = queryMinLength;
        state.clearInputAfterSelection = clearInputAfterSelection;
    }

    private final SuggestBoxClientRpc clientRpc = getRpcProxy(SuggestBoxClientRpc.class);

    @Override
    protected SuggestBoxState getState() {
        return (SuggestBoxState)super.getState();
    }

    protected abstract Set<SuggestionDto> getSuggestions(String query);

    @Override
    public void suggestFor(String query) {

        checkArgument(!isNullOrEmpty(query));

        Set<SuggestionDto> suggestionDtos = getSuggestions(query);

        clientRpc.showSuggestions(query, suggestionDtos.toArray(new SuggestionDto[suggestionDtos.size()]));
    }
}
