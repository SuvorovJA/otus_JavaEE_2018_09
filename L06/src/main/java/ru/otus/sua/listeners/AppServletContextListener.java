package ru.otus.sua.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.sua.helpers.EntytiesHelper6;
import ru.otus.sua.helpers.JpaHelper6;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

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
        JpaHelper6.saveEmployeEntity(
                EntytiesHelper6.createEmployeEntity(
                        "ADMIN", "TOMSK", 2000L,
                        "admin", "admin",
                        EntytiesHelper6.createAppointmentEntity("SysAdmin"),
                        EntytiesHelper6.createDepartmentEntity("IT Dept.")));
        log.info("Create user account on startup application.");
        JpaHelper6.saveEmployeEntity(
                EntytiesHelper6.createEmployeEntity(
                        "USER", "TOMSK", 1000L,
                        "user", "user",
                        EntytiesHelper6.createAppointmentEntity("User"),
                        EntytiesHelper6.createDepartmentEntity("Users")));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context
         (the Web application) is undeployed or
         Application Server shuts down.
      */
    }

}
