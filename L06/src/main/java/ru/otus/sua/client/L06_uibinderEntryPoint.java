package ru.otus.sua.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import ru.otus.sua.client.ui.Login;
import ru.otus.sua.client.ui.Main;

public class L06_uibinderEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        RootPanel.get("Container1").add(new Main());
        RootPanel.get("Container2").add(new Login());
    }
}


