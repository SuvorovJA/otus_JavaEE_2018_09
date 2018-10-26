package ru.otus.sua.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ru.otus.sua.shared.MyPair;

/**
 * The async counterpart of LoginService</code>.
 */
public interface LoginServiceAsync {
    void loginServer(MyPair<String, String> credentals, AsyncCallback<String> callback)
            throws IllegalArgumentException;
}
