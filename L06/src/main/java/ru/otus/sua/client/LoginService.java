package ru.otus.sua.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ru.otus.sua.shared.MyPair;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
    String loginServer(MyPair<String, String> credentals) throws IllegalArgumentException;
}
