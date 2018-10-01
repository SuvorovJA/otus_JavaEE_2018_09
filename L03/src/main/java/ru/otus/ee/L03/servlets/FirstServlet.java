package ru.otus.ee.L03.servlets;

import ru.otus.ee.L03.helpers.JpaHelper;

import javax.persistence.*;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/first", name = "FirstServlet")
public class FirstServlet extends HttpServlet {

    private static final String PERSISTENCE_UNIT_NAME = "JPAPersistenceUnit";
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); // for Tomcat

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("start servlet.<br>");

        EntityManager em = emf.createEntityManager(); // for Tomcat

        ServletContext context = request.getServletContext();

        try {
            out.println(JpaHelper.CreateAndSaveBigBoss(em));
            out.println(JpaHelper.LoadAndCreateDepartamentsFromCsvFile(em, context));
            out.println(JpaHelper.LoadAndCreateAppointmentsFromCsvFile(em, context));
            out.println(JpaHelper.LoadAndCreateEmployesFromCsvFile(em, context));
            out.println(JpaHelper.PrintAllEmployes(em));
            out.println(JpaHelper.ModifyTwoRandomEmployeeByMovingToTopManagement(em));
            out.println(JpaHelper.RemoveThreeRandomEmployee(em));
            out.println(JpaHelper.PrintAllEmployes(em));

            StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("get_fullname_with_max_salary");
            storedProcedure.registerStoredProcedureParameter("user",String.class, ParameterMode.OUT);
            storedProcedure.execute();
            out.println("PL/pqSQL: " + (String)storedProcedure.getOutputParameterValue("user") + "<br>");
        } finally {
            out.println("final servlet.<br>");
            em.close();
            out.close();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}
