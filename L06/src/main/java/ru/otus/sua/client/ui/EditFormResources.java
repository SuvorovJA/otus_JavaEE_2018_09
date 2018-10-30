package ru.otus.sua.client.ui;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface EditFormResources extends ClientBundle  {

    @Source("EditFormResources.css")
    EditFormResources.MyCss style();

    public interface MyCss extends CssResource {
        String buttonLinkReleased();

        String buttonLinkPressed();

        String labels();

        String inputs();

        String errors();

        String marpadding();
    }
}
