package ru.otus.sua.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import ru.otus.sua.client.ui.L06_uibinder;

public class L06_uibinderEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        RootPanel.get("Container1").add(new L06_uibinder());
    }
}


