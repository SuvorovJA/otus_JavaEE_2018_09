package ru.otus.sua.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import ru.otus.sua.client.LoginService;
import ru.otus.sua.entityes.EmployeEntity;
import ru.otus.sua.helpers.JpaHelper6;
import ru.otus.sua.shared.FieldVerifier;
import ru.otus.sua.shared.entities.MyPair;

import java.sql.SQLException;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

    // TODO on serverside not saved and not checked login status

    public String loginServer(MyPair<String, String> credentials) throws IllegalArgumentException {
        if (!FieldVerifier.isValidCredentials(credentials)) {
            throw new IllegalArgumentException("Валидация не пройдена");
        }

        // TODO Escape data from the client to avoid cross-site script vulnerabilities.
        //  String serverInfo = getServletContext().getServerInfo();
        //  String userAgent = getThreadLocalRequest().getHeader("User-Agent");
        //  input = escapeHtml(input);
        //  userAgent = escapeHtml(userAgent);
        EmployeEntity entity = null;
        try {
            entity = JpaHelper6.findByCredentials(credentials);
        } catch (SQLException e) {
            return e.getSQLState();
        }
        if (entity == null) {
            return "Не найдено..";  // TODO why not wrk?
        } else {
            return entity.getFullname();
        }
    }

    /**
     * Escape an html string. Escaping data received from the client helps to
     * prevent cross-site script vulnerabilities.
     *
     * @param html the html string to escape
     * @return the escaped string
     */
    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
                ">", "&gt;");
    }
}
