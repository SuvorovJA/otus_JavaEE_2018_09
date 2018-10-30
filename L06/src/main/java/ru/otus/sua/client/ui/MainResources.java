package ru.otus.sua.client.ui;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface MainResources extends ClientBundle {

    @Source("MainResources.css")
    MainCss style();

    public interface MainCss extends CssResource {
        String blackText();

        String redText();

        String someButton();

        String box();

        String backgroundMain();

        String backgroundSubmain();

        String backgroundSubsubmain();

        String backgroundDeck();

        String backgroundAside();

        String backgroundNav();

        String backgroundCurrency();

        String backgroundNews();

        String backgroundHeader();

        String backgroundFooter();

        String backgroundGrid();

        String footerText();

        String headerText();

        String bold();

        String marpadding();
    }


}
