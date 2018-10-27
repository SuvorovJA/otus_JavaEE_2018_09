package ru.otus.sua.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import ru.otus.sua.client.LoginService;
import ru.otus.sua.client.LoginServiceAsync;
import ru.otus.sua.shared.FieldVerifier;
import ru.otus.sua.shared.entities.MyPair;

public class Login extends Composite {

    // TODO 'remember me' not realized

    private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);
    @UiField(provided = true)
    final LoginResources res;
    private final LoginServiceAsync loginService = GWT.create(LoginService.class);
    private  final Label loginLabelOnNavigator;
    @UiField
    TextBox loginBox;
    @UiField
    TextBox passwordBox;
    @UiField
    Label completionLabel1;
    @UiField
    Label completionLabel2;
    private Boolean tooShort = false;

    public Login(Label label) {
        this.loginLabelOnNavigator = label;
        this.res = GWT.create(LoginResources.class);
        res.style().ensureInjected();
        initWidget(uiBinder.createAndBindUi(this));
    }

    //      Method name is not relevant, the binding is done according to the class of the parameter.
    @UiHandler("buttonSubmit")
    void doClickSubmit(ClickEvent event) {
        if (!tooShort) {
            sendLoginToServer();
        } else {
            Window.alert("Пароль или логин не прошли client-side проверку на валидность.");
        }
    }

    @UiHandler("loginBox")
    void handleLoginChange(ValueChangeEvent<String> event) {
        if (event.getValue().length() < 5) {
            completionLabel1.setText("Логин короток (нужно больше 5 символов)");
            tooShort = true;
        } else {
            tooShort = false;
            completionLabel1.setText("");
        }
    }

    @UiHandler("passwordBox")
    void handlePasswordChange(ValueChangeEvent<String> event) {
        if (event.getValue().length() < 5) {
            tooShort = true;
            completionLabel2.setText("Пароль короток (нужно больше 5 символов)");
        } else {
            tooShort = false;
            completionLabel2.setText("");
        }
    }

    private void sendLoginToServer() {

        MyPair<String, String> credentials = new MyPair<>();
        credentials.setLeft(loginBox.getText());
        credentials.setRight(passwordBox.getText());

        if (!FieldVerifier.isValidCredentials(credentials)) return;

        loginService.loginServer(credentials, new AsyncCallback<String>() {

            public void onFailure(Throwable caught) {
                loginLabelOnNavigator.setText("Ошибка: " + caught.getMessage());
            }

            public void onSuccess(String result) {
                if (result == null) {
                    loginLabelOnNavigator.setText("Не найдено...");
                }else{
                    loginLabelOnNavigator.setText("Вы вошли как: " + result);
                }
            }
        });
    }

    //     @UiTemplate is not mandatory but allows multiple XML templates to be used for the same widget.
    //     Default file loaded will be <class-name>.ui.xml
    @UiTemplate("Login.ui.xml")
    interface LoginUiBinder extends UiBinder<Widget, Login> {
    }

}
