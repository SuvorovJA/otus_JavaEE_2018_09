package ru.otus.sua.L07.entities.validation;

import javax.servlet.http.HttpServletRequest;

public class AuthHelper {
    public static synchronized boolean isAuthenticated(HttpServletRequest request, String errorString) {
        String siteUser = (String) request.getSession().getAttribute("AuthenticatedUser");
        if (siteUser == null) {
            request.setAttribute("errorString", errorString);
            return false;
        } else {
            request.removeAttribute("errorString");
            return true;
        }
    }

    public static synchronized boolean isAuthenticated(HttpServletRequest request) {
        String siteUser = (String) request.getSession().getAttribute("AuthenticatedUser");
        if (siteUser == null) {
            request.setAttribute("errorString", "Не произведен вход, исполнение не разрешено ");
            return false;
        } else {
            request.removeAttribute("errorString");
            return true;
        }
    }

    public static synchronized boolean isAuthenticatedWithInfo(HttpServletRequest request) {
        String siteUser = (String) request.getSession().getAttribute("AuthenticatedUser");
        if (siteUser == null) {
            request.getSession().setAttribute("errorString", "Не произведен вход");
            request.getSession().removeAttribute("infoString");
            return false;
        } else {
            request.getSession().setAttribute("infoString", "Произведен вход: " + siteUser);
            request.getSession().removeAttribute("errorString");
            return true;
        }
    }
}