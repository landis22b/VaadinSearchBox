package com.librishuffle.demo;

import com.librishuffle.SuggestBox;
import com.vaadin.annotations.Push;
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
@Push()
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

        final SuggestBox suggestBox = new CitySuggestBox();

        Layout suggestBoxLabelContainer = new HorizontalLayout();

        suggestBoxLabelContainer.addComponent(suggestBox);

        layout.addComponent(suggestBoxLabelContainer);
        layout.setComponentAlignment(suggestBoxLabelContainer, Alignment.MIDDLE_CENTER);
        setContent(layout);
    }
}
