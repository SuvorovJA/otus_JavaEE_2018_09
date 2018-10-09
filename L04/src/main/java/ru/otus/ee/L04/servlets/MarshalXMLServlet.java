package ru.otus.ee.L04.servlets;

import ru.otus.ee.L03.entityes.EmployeEntity;
import ru.otus.ee.L03.entityes.Employes;
import ru.otus.ee.L03.helpers.JpaHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(value = "/db2xml", name = "MarshalXMLServlet")
public class MarshalXMLServlet extends HttpServlet {

    private static final String FILE = "employes_jaxb.xml";
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

        try {
            Employes list = JpaHelper.getAllEmployes(em);
            out.println("Read from db " + list.getEmployes().size() + " Employes.<br>");
            out.println(JpaHelper.printAllEmployes(em));
            //
            JAXBContext context = JAXBContext.newInstance(Employes.class,EmployeEntity.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            File folder = (File) getServletContext().getAttribute(ServletContext.TEMPDIR);
            File file = new File(folder, FILE);
            out.println("Export to file: " + file.toString() + "<br>");
            out.println("File length before= " + file.length() + "<br>");
            m.marshal(list, file);
            out.println("File length after= " + file.length() + "<br>");
            //
        } catch (SQLException | JAXBException e) {
            e.printStackTrace();
        } finally {
            out.println("final servlet.<br>");
            em.close();
            out.close();
        }
    }
}
