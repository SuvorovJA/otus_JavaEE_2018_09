package ru.otus.sua.L07.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.sua.L07.entities.EmployeEntity;
import ru.otus.sua.L07.entities.helpers.EntitiesHelper;
import ru.otus.sua.L07.entities.helpers.EntityManagerHolder;
import ru.otus.sua.L07.entities.helpers.JpaDTO;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
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
        log.info("Create admin account on startup application.");
        try {
            saveEmployeEntity(
                    EntitiesHelper.createEmployeEntity(
                            "A. DMINSKY",
                            new SimpleDateFormat("dd.MM.yyyy").parse("15.05.1975"),
                            "TOMSK", 2000L,
                            "admin", "admin",
                            EntitiesHelper.createAppointmentEntity("SysAdmin"),
                            EntitiesHelper.createDepartmentEntity("IT Dept.")));
            log.info("Create user account on startup application.");
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

        // TODO logger not work on shutdown tomcat
        log.info("Erase database content on App Shutdown.");
        System.out.println("Console> Erase database content on App Shutdown.");
        try {
            for (EmployeEntity entity : readAllEmployes().getEmployes()) {
                deleteEmployeEntity(entity);
            }
        } catch (SQLException e) {
            log.error("Err on DB erase in contextDestroyed.");
        }

        log.info("Shutdown lucene indexer.");
        System.out.println("Console> Shutdown lucene indexer.");
        EntityManagerHolder.shutdownIndexer();

    }

}
