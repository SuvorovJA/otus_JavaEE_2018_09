package ru.otus.ee.L04.servlets;

import ru.otus.ee.L03.helpers.JpaHelper;
import ru.otus.ee.L03.entityes.EmployeEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/db2xml", name = "MarshalXMLServlet")
public class MarshalXMLServlet extends HttpServlet {

    private static final String PERSISTENCE_UNIT_NAME = "JPAPersistenceUnit";
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("start servlet.<br>");
        EntityManager em = emf.createEntityManager();
        List<EmployeEntity> list = new ArrayList<>();
        try {
            list = JpaHelper.getAllEmployes(em);
            out.println("Read from db " + list.size() + " Employes.<br>");
            out.println(JpaHelper.printAllEmployes(em));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            out.println("final servlet.<br>");
            em.close();
            out.close();
        }
    }
}
