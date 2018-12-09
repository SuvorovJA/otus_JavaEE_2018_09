package ru.otus.sua.L07.jaxws;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class InformatoryServiceTest {
    Informatory informatory;

    @Before
    public void setUp() throws Exception {
        URL wsdlLocation = new URL("http://localhost:8080/L08/InformatoryService?WSDL");
        String namespace = "http://jaxws.L07.sua.otus.ru/";
        String service = "InformatoryServiceService";
        String port = "InformatoryServicePort";
        QName serviceQN = new QName(namespace, service);
        QName portQN = new QName(namespace, port);
        Service jaxwsService = Service.create(wsdlLocation, serviceQN);
        informatory = jaxwsService.getPort(portQN, Informatory.class);
    }

    @After
    public void tearDown() throws Exception {
        assertEquals(1470.0, informatory.getAvgSalary(), 0.01);
    }

    @Test
    public void getAvgSalary() {
    }
}