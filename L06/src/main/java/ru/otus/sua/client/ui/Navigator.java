package ru.otus.sua.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

public class Navigator extends Composite implements ClickHandler {

    final private Button mainButton = new Button();
    final private Button employesButton = new Button();
    final private Button aboutButton = new Button();
    final private Button loginButton = new Button();
    final private HorizontalPanel panel = new HorizontalPanel();
    final private Label loginLabel;
    final private DeckPanel deckPanel;

    public Navigator(DeckPanel deckPanel, Label loginLabel) {

        this.deckPanel = deckPanel;
        this.loginLabel = loginLabel;

        aboutButton.addClickHandler(this::onClick);
        employesButton.addClickHandler(this::onClick);
        mainButton.addClickHandler(this::onClick);
        loginButton.addClickHandler(this::onClick);

        setDefaultStyle();
        loginLabel.setStyleName("buttonLink4");
        mainButton.setStyleName("buttonLink3");
        deckPanel.showWidget(0);

        mainButton.setText("Главная");
        aboutButton.setText("Про");
        employesButton.setText("Работники");
        loginButton.setText("Вход");
        loginLabel.setText("не залогинились");

        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        panel.add(mainButton);
        panel.add(employesButton);
        panel.add(aboutButton);
        panel.add(loginButton);
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT); // TODO dont work
        panel.add(loginLabel);

        initWidget(panel);
    }

    @Override
    public void onClick(ClickEvent event) {
        final Object sender = event.getSource();
        setDefaultStyle();

        if (sender == mainButton) {
            mainButton.setStyleName("buttonLink3");
            deckPanel.showWidget(0);
        } else if (sender == aboutButton) {
            aboutButton.setStyleName("buttonLink3");
            deckPanel.showWidget(2);
        } else if (sender == employesButton) {
            employesButton.setStyleName("buttonLink3");
            deckPanel.showWidget(1);
        } else if (sender == loginButton) {
            loginButton.setStyleName("buttonLink3");
            deckPanel.showWidget(3);
        }
    }

    private void setDefaultStyle() {
        mainButton.setStyleName("buttonLink2");
        aboutButton.setStyleName("buttonLink2");
        employesButton.setStyleName("buttonLink2");
        loginButton.setStyleName("buttonLink2");
    }
}
