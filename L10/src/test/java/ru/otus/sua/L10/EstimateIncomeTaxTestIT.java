package ru.otus.sua.L10;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class EstimateIncomeTaxTestIT {
    Estimator estimator;
    Endpoint endpoint;

    @BeforeEach
    void setUp() throws MalformedURLException {
        endpoint = Endpoint.publish("http://localhost:8081/TaxEstimatorService", new EstimateIncomeTax());
        assertTrue(endpoint.isPublished());
        assertEquals("http://schemas.xmlsoap.org/wsdl/soap/http", endpoint.getBinding().getBindingID());

        URL wsdlLocation = new URL("http://localhost:8081/TaxEstimatorService?WSDL");
        String namespace = "http://L10.sua.otus.ru/";
        String service = "EstimateIncomeTaxService";
        String port = "EstimateIncomeTaxPort";
        QName serviceQN = new QName(namespace, service);
        QName portQN = new QName(namespace, port);

        Service jaxwsService = Service.create(wsdlLocation, serviceQN);
        estimator = jaxwsService.getPort(portQN,Estimator.class);
    }

    @Test
    void estimateIncomeTax() throws InterruptedException {
//        Thread.sleep(1111111111);
        TaxData taxData = new TaxData(1000000.0, 200000.0, 20.0);
        assertEquals(160000.0, estimator.estimateIncomeTax(taxData));
    }

    @AfterEach
    void shutdown() {
        endpoint.stop();
        assertFalse(endpoint.isPublished());
    }
}