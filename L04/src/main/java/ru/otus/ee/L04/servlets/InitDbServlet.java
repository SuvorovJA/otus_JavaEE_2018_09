package ru.otus.ee.L04.servlets;

import ru.otus.ee.L04.helpers.JpaHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/dbinit", name = "InitDbServlet")
public class InitDbServlet extends HttpServlet {
    private static final String PERSISTENCE_UNIT_NAME = "JPAPersistenceUnit";
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("start servlet.<br>");
        EntityManager em = emf.createEntityManager();
        ServletContext context = request.getServletContext();

        try {
            out.println(JpaHelper.loadAndCreateDepartamentsFromCsvFile(em, context));
            out.println(JpaHelper.loadAndCreateAppointmentsFromCsvFile(em, context));
            out.println(JpaHelper.loadAndCreateEmployesFromCsvFile(em, context));
        } finally {
            out.println("final servlet.<br>");
            em.close();
            out.close();
        }
    }
}
