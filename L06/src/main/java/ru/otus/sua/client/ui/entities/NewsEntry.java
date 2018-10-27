package ru.otus.sua.client.ui.entities;

import com.google.gwt.core.client.JavaScriptObject;

public class NewsEntry extends JavaScriptObject {

    protected NewsEntry() {
    }

    public final native String getText() /*-{
        return this.text;
    }-*/;

    public final native String getHref() /*-{
        return this.href;
    }-*/;

}
