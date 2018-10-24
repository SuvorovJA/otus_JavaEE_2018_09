package ru.otus.sua.client;

// DECKPANEL SAMPLE

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

public class L06_deckpanel implements EntryPoint {

    public void onModuleLoad() {
        // Create DeckPanel widget
        final DeckPanel deckPanel = new DeckPanel();
        deckPanel.setSize("300px", "120px");
        deckPanel.setStyleName("deckpanel");

        // Create lables to add to deckpanel
        Label label1 = new Label("This is first Page");
        Label label2 = new Label("This is second Page");
        Label label3 = new Label("This is third Page");

        // Add labels to deckpanel
        deckPanel.add(label1);
        deckPanel.add(label2);
        deckPanel.add(label3);

        //show first label
        deckPanel.showWidget(0);

        //create button bar
        HorizontalPanel buttonBar = new HorizontalPanel();
        buttonBar.setSpacing(5);

        // create button and add click handlers
        // show different labels on click of different buttons
        Button button1 = new Button("Page 1");
        button1.setStyleName("buttonLink");
        button1.addClickHandler(event -> deckPanel.showWidget(0));

        Button button2 = new Button("Page 2");
        button2.addClickHandler(event -> deckPanel.showWidget(1));
        button2.setStyleName("buttonLink2");

        Button button3 = new Button("Page 3");
        button3.addClickHandler(event -> deckPanel.showWidget(2));
//        button3.setStyleName("buttonLink2");

        buttonBar.add(button1);
        buttonBar.add(button2);
        buttonBar.add(button3);

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(deckPanel);
        vPanel.add(buttonBar);

        // Add the widgets to the root panel.
        RootPanel.get("Container1").add(vPanel);
    }
}