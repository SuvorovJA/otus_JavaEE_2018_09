package ru.otus.sua.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NewsFeeder {

    private final MainResources res;
    private final VerticalPanel newsPanel;

    public NewsFeeder(VerticalPanel newsPanel, MainResources res) {
        this.newsPanel = newsPanel;
        this.res = res;
    }

    // TODO sheduling
    public void FillNewsPanel() {
        String url = GWT.getHostPageBaseURL() + "news";
        JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
        AsyncCallback<Feed> asyncCallback = new AsyncCallback<Feed>() {
            public void onFailure(Throwable throwable) {
                newsPanel.add(new Label("ошибка"));
            }

            public void onSuccess(Feed feed) {
                JsArray<NewsEntry> entries = feed.getEntries();
                for (int i = 0; i < entries.length(); i++) {
                    NewsEntry entry = entries.get(i);
                    Anchor anchor = new Anchor(entry.getText(), entry.getHref());
                    SimplePanel wrap = new SimplePanel(anchor);
                    wrap.setStyleName(res.style().marpadding());
                    newsPanel.add(wrap);
                }
            }
        };
        jsonp.requestObject(url, asyncCallback);
    }

    static class Feed extends JavaScriptObject {
        protected Feed() {
        }

        public final native JsArray<NewsEntry> getEntries() /*-{
            //console.log(this);
            return this;
        }-*/;
    }

    static class NewsEntry extends JavaScriptObject {

        protected NewsEntry() {
        }

        public final native String getText() /*-{
            return this.text;
        }-*/;

        public final native String getHref() /*-{
            return this.href;
        }-*/;

    }

}
