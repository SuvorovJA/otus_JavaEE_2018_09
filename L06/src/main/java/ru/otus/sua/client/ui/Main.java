package ru.otus.sua.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;


public class Main extends Composite {

    private static MainUiBinder uiBinder = GWT.create(MainUiBinder.class);
    @UiField(provided = true)
    final MainResources res;
    @UiField
    HTML footerPanel;
    @UiField
    HTML headerPanel;
    @UiField
    HorizontalPanel navigationPanel;
    @UiField
    VerticalPanel currencyPanel;
    @UiField
    VerticalPanel newsPanel;
    @UiField
    DeckPanel deckPanel;

    public Main() {
        this.res = GWT.create(MainResources.class);
        res.style().ensureInjected();
        initWidget(uiBinder.createAndBindUi(this));
        footerPanel.setHTML(FooterHtmlResource.INSTANCE.getHtml().getText());
        headerPanel.setHTML(HeaderHtmlResource.INSTANCE.getHtml().getText());
        deckPanelInit();
        navigationPanel.add(new Navigator(deckPanel));
    }

    private void deckPanelInit() {
        // Create lables to add to deckpanel
        Label label1 = new Label("This is first Page");
        Label label2 = new Label("This is second Page");
        Label label3 = new Label("This is third Page");
        Label label4 = new Label("This is fourth Page");

        // Add labels to deckpanel
        deckPanel.add(label1);
        deckPanel.add(label2);
        deckPanel.add(label3);
        deckPanel.add(label4);

        //show first label
        //deckPanel.showWidget(0);
    }


    //     @UiTemplate is not mandatory but allows multiple XML templates to be used for the same widget.
    //     Default file loaded will be <class-name>.ui.xml
    @UiTemplate("Main.ui.xml")
    interface MainUiBinder extends UiBinder<Widget, Main> {
    }
}
