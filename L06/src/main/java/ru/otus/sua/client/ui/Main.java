package ru.otus.sua.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.*;


public class Main extends Composite {

    private static MainUiBinder uiBinder = GWT.create(MainUiBinder.class);
    @UiField(provided = true)
    final MainResources res;
    private final Navigator navigator;
    private final Label loginLabelOnNavigator = new Label();
    private final Login loginWidget;
    private final NewsFeeder newsFeeder;
    private final CurrencyFeeder currencyFeeder;
    private final EmployesListFeeder employesListFeeder;
    private final VerticalPanel employesGridPanel = new VerticalPanel();
    private final DataGrid<EmployesListFeeder.EmployeEntry> dataGrid = new DataGrid<>();
    private final Label loginLabelOnEmployesGridPanel = new Label();
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

        dataGrid.setStyleName(res.style().backgroundGrid());

        loginLabelOnEmployesGridPanel.setText("Нужно произвести вход.");
        employesGridPanel.add(loginLabelOnEmployesGridPanel);

        HTML label1 = new HTML("Это главная страница. <br> Первый пользователь ADMIN admin/admin <br> <br>Тут новости компании. ");
        Label label2 = new Label("Страница про компанию");
        Label label3 = new Label("Страница логина");
        deckPanel.add(label1);
        deckPanel.add(employesGridPanel);
        deckPanel.add(label2);
        deckPanel.add(label3); // need 4 widgets in deckpanel

        loginWidget = new Login(loginLabelOnNavigator);

        navigator = new Navigator(deckPanel,
                                    loginWidget,
                                    loginLabelOnNavigator,
                                    loginLabelOnEmployesGridPanel,
                                    dataGrid,
                                    employesGridPanel);
        navigationPanel.add(navigator);

        deckPanel.remove(label3);
        deckPanel.add(loginWidget);

        employesListFeeder = new EmployesListFeeder(employesGridPanel, dataGrid, res);
        currencyFeeder = new CurrencyFeeder(currencyPanel, res);
        newsFeeder = new NewsFeeder(newsPanel, res);

        currencyFeeder.FillCurrencyPanel();
        newsFeeder.FillNewsPanel();
        employesListFeeder.FillEmployeePanel();

    }

    //     @UiTemplate is not mandatory but allows multiple XML templates to be used for the same widget.
    //     Default file loaded will be <class-name>.ui.xml
    @UiTemplate("Main.ui.xml")
    interface MainUiBinder extends UiBinder<Widget, Main> {
    }
}
