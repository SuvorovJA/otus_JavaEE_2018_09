package ru.otus.sua.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import ru.otus.sua.client.ui.entities.Feed;
import ru.otus.sua.client.ui.entities.NewsEntry;

public class NewsFeeder {

    VerticalPanel newsPanel;

    public NewsFeeder(VerticalPanel newsPanel) {
        this.newsPanel = newsPanel;
    }

    // TODO sheduling
    public void FillNewsPanel() {

        String url = GWT.getHostPageBaseURL() + "news";
        JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
        jsonp.requestObject(url, new AsyncCallback<Feed>() {

            public void onFailure(Throwable throwable) {
                newsPanel.add(new Label("ошибка"));
            }

            public void onSuccess(Feed feed) {
                JsArray<NewsEntry> entries = feed.getEntries();
                for (int i = 0; i < entries.length(); i++) {
                    if (i > 10) break; // TODO rm hardcoded limit
                    NewsEntry entry = entries.get(i);
                    newsPanel.add(new NewsAnchor(entry.getText(), entry.getHref()));
                }
            }
        });
    }

    class NewsAnchor extends Widget {
        private Anchor anchor;

        public NewsAnchor(String text, String href) {
            anchor = new Anchor(text);
            anchor.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    Window.open(href, "_blank", "");
                }
            });
        }
    }

}
