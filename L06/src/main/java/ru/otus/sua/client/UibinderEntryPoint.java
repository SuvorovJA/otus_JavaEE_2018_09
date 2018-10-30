package ru.otus.sua.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import ru.otus.sua.client.ui.Main;

public class UibinderEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        RootPanel.get("Container").add(new Main());
    }
}


