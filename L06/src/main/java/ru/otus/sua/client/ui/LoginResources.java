package ru.otus.sua.client.ui;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface LoginResources extends ClientBundle {

    @Source("LoginResources.css")
    MyCss style();

    public interface MyCss extends CssResource {
        String blackText();

        String redText();

        String loginButton();

        String box();

        String background();
    }


}
