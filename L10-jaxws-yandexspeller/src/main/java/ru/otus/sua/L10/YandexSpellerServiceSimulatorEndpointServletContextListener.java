package ru.otus.sua.L10;

import com.sun.xml.internal.bind.v2.TODO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.xml.ws.Endpoint;

@WebListener
@Slf4j
public class YandexSpellerServiceSimulatorEndpointServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            String webServiceUrl = "http://localhost:8085" + sce.getServletContext().getContextPath() + "/speller";
            Endpoint.publish(webServiceUrl, new YandexSpellerServiceAsRESTSimulator());
            // TODO Webservice Endpoint deployed ru.otus.sua.L10.YandexSpellerServiceAsRESTSimulator listening at address
            // http://b28d2e63012d:8080/L10-jaxws-yandexspeller_war/YandexSpellerServiceAsRESTSimulator.
            // http://localhost:8085--> ERR_CONNECTION_REFUSED
        } catch (Exception e) {
            log.error("CANT RUN YANDEX SPELLER REST WRAPPER: {}",e.getMessage());
        }
    }
}
