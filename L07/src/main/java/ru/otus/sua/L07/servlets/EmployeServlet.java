package ru.otus.sua.L07.servlets;

import ru.otus.sua.L07.entities.Employes;
import ru.otus.sua.L07.entities.helpers.JpaDtoForEmployeEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "EmployeServlet", urlPatterns = "/employeServlet")
public class EmployeServlet extends HttpServlet {

//    here will login check and redirect to login page


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        Employes employes = null;
        try {
            if (isSearch(request)) {
                employes = JpaDtoForEmployeEntity.queryEmployes(
                        request.getParameter("search_fullName"),
                        request.getParameter("search_city"),
                        request.getParameter("search_departament"),
                        request.getParameter("search_appointment"),
                        request.getParameter("search_login"));
            } else {
                employes = JpaDtoForEmployeEntity.readAllEmployes();
            }
        } catch (SQLException e) {
            errorString = e.getMessage();
        }
        if (errorString != null) request.setAttribute("errorString", errorString);
        request.setAttribute("employes", employes.getEmployes());
        replaySearchFields(request);
    }

    private boolean isSearch(HttpServletRequest request) {
        return (request.getParameter("search_fullName") != null &&
                !request.getParameter("search_fullName").isEmpty()) ||
                (request.getParameter("search_city") != null &&
                        !request.getParameter("search_city").isEmpty()) ||
                (request.getParameter("search_departament") != null &&
                        !request.getParameter("search_departament").isEmpty()) ||
                (request.getParameter("search_appointment") != null &&
                        !request.getParameter("search_appointment").isEmpty()) ||
                (request.getParameter("search_login") != null &&
                        !request.getParameter("search_login").isEmpty());
    }

    private void replaySearchFields(HttpServletRequest request) {
        request.setAttribute("search_fullName", request.getParameter("search_fullName"));
        request.setAttribute("search_age", request.getParameter("search_age"));
        request.setAttribute("search_city", request.getParameter("search_city"));
        request.setAttribute("search_department", request.getParameter("search_department"));
        request.setAttribute("search_appointment", request.getParameter("search_appointment"));
        request.setAttribute("search_login", request.getParameter("search_login"));
    }

}
