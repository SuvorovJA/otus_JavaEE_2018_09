package ru.otus.sua.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;


public interface HeaderHtmlResource extends ClientBundle {
    public static final HeaderHtmlResource INSTANCE = GWT.create(HeaderHtmlResource.class);

    @Source("_header.html")
    public TextResource getHtml();

}