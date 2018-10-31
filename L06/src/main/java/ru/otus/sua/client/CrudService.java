package ru.otus.sua.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ru.otus.sua.shared.entities.MyPair;

import javax.json.JsonObject;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("crud")
public interface CrudService extends RemoteService {
    String deleteEmploye(String json) throws IllegalArgumentException;
    String createEmploye(String json) throws IllegalArgumentException;
    String editEmploye(String json) throws IllegalArgumentException;
    String readEmploye(String json) throws IllegalArgumentException;
}
