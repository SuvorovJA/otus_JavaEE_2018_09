package ru.otus.sua.L07.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.sua.L07.entities.EmployeEntity;
import ru.otus.sua.L07.entities.helpers.EntitiesHelper;

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
        } catch (ParseException e) {
            e.printStackTrace();
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
    }

}
