package ru.otus.sua.servlets;

import ru.otus.sua.entities.EmployeEntity;
import ru.otus.sua.entities.Employes;
import ru.otus.sua.helpers.JpaDTO;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EmployesListServlet", urlPatterns = "/allemployes")
public class EmployesListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Employes employes = JpaDTO.readAllEmployes();
            // without jackson
            JsonArrayBuilder aBuilder = Json.createArrayBuilder();
            for (EmployeEntity e : employes.getEmployes()){
                JsonObjectBuilder oBuilder = Json.createObjectBuilder();
                oBuilder.add("id",e.getId());
                oBuilder.add("fullName",e.getFullname());
                oBuilder.add("salary",e.getSalary());
                oBuilder.add("city",e.getCity());
                oBuilder.add("department",e.getDepartment().getName());
                oBuilder.add("appointment",e.getAppointment().getName());
                oBuilder.add("login",e.getCredentials().getLogin());
                oBuilder.add("passhash",e.getCredentials().getPasshash());
                oBuilder.add("departmentId",e.getDepartment().getId());
                oBuilder.add("appointmentId",e.getAppointment().getId());
                oBuilder.add("credentialsId",e.getCredentials().getId());
                aBuilder.add(oBuilder.build());
            }
            String callback = request.getParameter("callback");
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(callback + "(" + aBuilder.build() + ");");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
