package ru.otus.sua.client.ui;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface MainResources extends ClientBundle {

    @Source("MainResources.css")
    MyCss style();

    public interface MyCss extends CssResource {
        String blackText();

        String redText();

        String loginButton();

        String box();

        String background();
    }


}
