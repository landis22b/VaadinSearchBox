package com.librishuffle.demo;

import com.librishuffle.SuggestBox;
import com.librishuffle.client.SelectionChangedHandler;
import com.librishuffle.client.Suggestion;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;

@Theme("demo")
@Title("SuggestBox Add-on Demo")
@SuppressWarnings("serial, unused")
public class DemoUI extends UI{
    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "com.librishuffle.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {

        VerticalLayout layout = new VerticalLayout();

        layout.setSizeFull();

        Label label = new Label("City:  ");

        final SuggestBox suggestBox = new CitySuggestBox();

        suggestBox.addSelectionChangedListener(
                new SelectionChangedHandler() {
                    @Override
                    public void onSelectionChanged(Suggestion suggestion) {
                        Notification.show(suggestion.getDisplayString() + " selected");
                    }
                }
        );

        Link linkToGitHub = new Link("view code on Github", new ExternalResource("https://github.com/brndnbg/VaadinSearchBox/blob/master/vaadin-suggest-demo/src/main/java/com/librishuffle/demo/CitySuggestBox.java"));
        linkToGitHub.setPrimaryStyleName("linkToGithub");

        Layout suggestBoxLabelContainer = new HorizontalLayout();

        suggestBoxLabelContainer.addComponent(label);
        suggestBoxLabelContainer.addComponent(suggestBox);

        layout.addComponent(suggestBoxLabelContainer);
        layout.setComponentAlignment(suggestBoxLabelContainer, Alignment.MIDDLE_CENTER);
        layout.addComponent(linkToGitHub);
        setContent(layout);
    }
}
