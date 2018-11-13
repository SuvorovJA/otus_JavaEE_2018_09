package ru.otus.sua.L07.servlets;

import ru.otus.sua.L07.entities.EmployeSearchPacket;
import ru.otus.sua.L07.entities.Employes;
import ru.otus.sua.L07.entities.exceptions.InvalidSearchException;
import ru.otus.sua.L07.entities.validation.AuthHelper;
import ru.otus.sua.L07.entities.helpers.EmployeEntityDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "EmployesServlet", urlPatterns = "/employes")
public class EmployesServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (AuthHelper.isAuthenticated(request)) {

            String errorString = "";
            Employes employes = null;
            EmployeSearchPacket searchPacket = new EmployeSearchPacket();
            try {
                searchPacket.setFromRequest(request);
            } catch (InvalidSearchException e) {
                errorString += e.getMessage() + "; ";
            }

            try {
                if (searchPacket.isSearchable()) {
                    employes = EmployeEntityDAO.queryEmployes(searchPacket);
                } else {
                    employes = EmployeEntityDAO.readAllEmployes();
                }
            } catch (SQLException e) {
                errorString += e.getMessage() + "; ";
            }

            if (!errorString.isEmpty()) request.setAttribute("errorString", errorString);

            request.setAttribute("employes", employes.getEmployes());

            replaySearchFields(request);

        }
    }


    private void replaySearchFields(HttpServletRequest request) {
        request.setAttribute("search_fullName", request.getParameter("search_fullName"));
        request.setAttribute("search_age_min", request.getParameter("search_age_min"));
        request.setAttribute("search_age_max", request.getParameter("search_age_max"));
        request.setAttribute("search_city", request.getParameter("search_city"));
        request.setAttribute("search_departament", request.getParameter("search_departament"));
        request.setAttribute("search_appointment", request.getParameter("search_appointment"));
        request.setAttribute("search_login", request.getParameter("search_login"));
    }

}
