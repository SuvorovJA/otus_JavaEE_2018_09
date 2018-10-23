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
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed).
         You can initialize servlet context related data here.
      */
        log.info("Create admin account on startup application.");
        JpaHelper6.saveEmploye(
                EntytiesHelper6.createEmployeEntity(
                        "ADMIN", "TOMSK", 1L,
                        "admin", "admin",
                        EntytiesHelper6.createAppointmentEntity("SysAdmin"),
                        EntytiesHelper6.createDepartmentEntity("IT Dept.")));
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context
         (the Web application) is undeployed or
         Application Server shuts down.
      */
    }

}
