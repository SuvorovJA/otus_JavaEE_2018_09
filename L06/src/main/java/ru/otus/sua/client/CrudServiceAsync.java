package ru.otus.sua.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ru.otus.sua.shared.entities.MyPair;

import javax.json.JsonObject;

public interface CrudServiceAsync {
    void deleteEmploye(String json, AsyncCallback<String> async);

    void createEmploye(String json, AsyncCallback<String> async);

    void editEmploye(String json, AsyncCallback<String> async);

    void readEmploye(String json, AsyncCallback<String> async);
}
