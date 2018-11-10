package ru.otus.sua.L07.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.sua.L07.entities.EmployeEntity;
import ru.otus.sua.L07.entities.Employes;
import ru.otus.sua.L07.entities.helpers.EntitiesHelper;
import ru.otus.sua.L07.entities.helpers.EntityManagerHolder;
import ru.otus.sua.L07.entities.helpers.JpaDtoForEmployeEntity;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static ru.otus.sua.L07.entities.helpers.JpaDtoForEmployeEntity.*;

@WebListener()
public class AppServletContextListener implements ServletContextListener {

    private static Logger log = LoggerFactory.getLogger(AppServletContextListener.class);

    // Public constructor is required by servlet spec
    public AppServletContextListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    @Override
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed).
         You can initialize servlet context related data here.
      */
        URL resourceUrl = null;
        try {
            resourceUrl = sce.getServletContext().getResource("/WEB-INF/classes/employes_jaxb.xml");
        } catch (MalformedURLException e) {
            log.info("Cant check xml template for DB. " + e.getMessage());
        }

        if (resourceUrl != null) {
            File inputFile = new File(resourceUrl.getFile());
            if (inputFile.exists()) {
                log.info("Import DB content on startup application.");
                try {
                    Employes employes = unmarshalingDB(inputFile);
                    log.info("Imported " + employes.getEmployes().size() + " records");
                    for (EmployeEntity e : employes.getEmployes()) {
                        saveEmployeEntity(
                                EntitiesHelper.createEmployeEntity(
                                        e.getFullName(),
                                        e.getDateOfBirth(),
                                        e.getCity(),
                                        e.getSalary(),
                                        e.getCredentials().getLogin(),
                                        e.getCredentials().getPasshash(),
                                        EntitiesHelper.createAppointmentEntity(e.getAppointment().getName()),
                                        EntitiesHelper.createDepartmentEntity(e.getDepartament().getName())));

                    }
                } catch (SQLException | JAXBException e) {
                    log.error("Cant import xml template for DB. ");
                }

                return;

            }
        }


        log.info("Create hardcoded admin account on startup application.");
        try {
            saveEmployeEntity(
                    EntitiesHelper.createEmployeEntity(
                            "A. DMINSKY",
                            new SimpleDateFormat("dd.MM.yyyy").parse("15.05.1975"),
                            "TOMSK", 2000L,
                            "admin", "admin",
                            EntitiesHelper.createAppointmentEntity("SysAdmin"),
                            EntitiesHelper.createDepartmentEntity("IT Dept.")));
            log.info("Create hardcoded user account on startup application.");
            saveEmployeEntity(
                    EntitiesHelper.createEmployeEntity(
                            "U. USER",
                            new SimpleDateFormat("dd.MM.yyyy").parse("09.11.1998"),
                            "NSK", 1000L,
                            "user", "user",
                            EntitiesHelper.createAppointmentEntity("User"),
                            EntitiesHelper.createDepartmentEntity("Users")));
        } catch (ParseException e) {
            log.error(e.getMessage());
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context
         (the Web application) is undeployed or
         Application Server shuts down.
      */


        try {
            final File fullPathToXml = getFullPathFileToXml(sce);
            marshalingDB(fullPathToXml);
            loggerOnShutdown("Database exported to: " + fullPathToXml.toString());
        } catch (SQLException | JAXBException e) {
            loggerOnShutdown("Err when export database. " + e.getMessage());
        }


        loggerOnShutdown("Erase database content on App Shutdown.");
        try {
            for (EmployeEntity entity : readAllEmployes().getEmployes()) {
                deleteEmployeEntity(entity);
            }
        } catch (SQLException e) {
            loggerOnShutdown("Err on DB erasing.");
        }


        loggerOnShutdown("Shutdown lucene indexer.");
        EntityManagerHolder.shutdownIndexer();

    }

    private File getFullPathFileToXml(ServletContextEvent sce) {
        // TODO parametrise out-xml file name
        final String FILE = "employes_jaxb.xml";
        final File writableFolder = (File) sce.getServletContext().getAttribute(ServletContext.TEMPDIR);
        return new File(writableFolder, FILE);
    }

    private void loggerOnShutdown(String msg) {
        // TODO logger not work on shutdown tomcat
        log.info(msg);
        System.out.println("OnShutdownApplication> " + msg);
    }

    private void marshalingDB(File outputFile) throws SQLException, JAXBException {
        Employes allEmployes = JpaDtoForEmployeEntity.readAllEmployes();
        JAXBContext context = JAXBContext.newInstance(Employes.class, EmployeEntity.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(allEmployes, outputFile);
    }

    private Employes unmarshalingDB(File inputFile) throws SQLException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(Employes.class, EmployeEntity.class);
        Unmarshaller m = context.createUnmarshaller();
        Employes allEmployes = (Employes) m.unmarshal(inputFile);
        return allEmployes;
    }
}
