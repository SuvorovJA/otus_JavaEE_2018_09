package ru.otus.sua.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class Main extends Composite {

    private static MainUiBinder uiBinder = GWT.create(MainUiBinder.class);

    //     @UiTemplate is not mandatory but allows multiple XML templates to be used for the same widget.
    //     Default file loaded will be <class-name>.ui.xml
    @UiTemplate("Main.ui.xml")
    interface MainUiBinder extends UiBinder<Widget, Main> {
    }

    @UiField(provided = true)
    final MainResources res;

    public Main() {
        this.res = GWT.create(MainResources.class);
        res.style().ensureInjected();
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    TextBox loginBox;

    @UiField
    TextBox passwordBox;

    @UiField
    Label completionLabel1;

    @UiField
    Label completionLabel2;

    private Boolean tooShort = false;

    //      Method name is not relevant, the binding is done according to the class of the parameter.
    @UiHandler("buttonSubmit")
    void doClickSubmit(ClickEvent event) {
        if (!tooShort) {
            Window.alert("Login Successful!");
        } else {
            Window.alert("Login or Password is too short!");
        }
    }

    @UiHandler("loginBox")
    void handleLoginChange(ValueChangeEvent<String> event) {
        if (event.getValue().length() < 6) {
            completionLabel1.setText("Login too short (Size must be > 6)");
            tooShort = true;
        } else {
            tooShort = false;
            completionLabel1.setText("");
        }
    }

    @UiHandler("passwordBox")
    void handlePasswordChange(ValueChangeEvent<String> event) {
        if (event.getValue().length() < 6) {
            tooShort = true;
            completionLabel2.setText("Password too short (Size must be > 6)");
        } else {
            tooShort = false;
            completionLabel2.setText("");
        }
    }

}