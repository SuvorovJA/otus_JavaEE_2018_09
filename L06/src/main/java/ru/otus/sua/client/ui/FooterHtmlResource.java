package ru.otus.sua.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;


public interface FooterHtmlResource extends ClientBundle {
    public static final FooterHtmlResource INSTANCE = GWT.create(FooterHtmlResource.class);

    @Source("_footer.html")
    public TextResource getHtml();

}