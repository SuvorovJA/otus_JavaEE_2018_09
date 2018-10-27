package ru.otus.sua.client.ui.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class Feed extends JavaScriptObject {
    protected Feed() {
    }

    //  FOR NAMED ARRAY (see NewsServlet)
    //console.log(this.news); //[{…}]
    //                           0:{text: "Jimmy", href: "Webber"}
    //                           length:1
    //
    //return this.feed.news;//Uncaught TypeError: Cannot read property 'news' of undefined
    //return this.news;//Uncaught TypeError: Cannot read property '__gwt_resolve' of undefined
    //    __gwt_jsonp__.P0.onSuccess({
    //        "news": [{
    //            "text": "Jimmy",
    //            "href": "Webber"
    //        }]
    //    });


    //  FOR ARRAY
    //console.log(this);//[{…}]
    //                        0:{text: "Jimmy", href: "Webber"}
    //                        length:1
    //return this;//Uncaught TypeError: Cannot read property '__gwt_resolve' of undefined


    public final native JsArray<NewsEntry> getEntries() /*-{
        console.log(this);
        return this;
    }-*/;
}