package ru.otus.sua.L11.web;

import lombok.Data;
import ru.otus.sua.L11.entity.Estimates;
import ru.otus.sua.L11.entity.ValuePack;
import ru.otus.sua.L11.web.util.JsfUtil;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.ResourceBundle;

@Named
@SessionScoped
@Data
public class CreditController implements Serializable {
    private static final long serialVersionUID = -8163374738411860014L;

    private static final String CREDIT_CALC_SITE = "http://localhost:8080/L11-jaxrs-service_Web/";
    private static final String CREDIT_CALC_SERVICE = "/monthlyPayment";

    private DataModel items = null;

    private ValuePack valuePack = new ValuePack(6, 10000., 15.);

    private String version = "v1";

    public String sendGet() {
        try {
            Estimates estimates = ClientBuilder.newClient().target(CREDIT_CALC_SITE + version + CREDIT_CALC_SERVICE)
                    .path(String.valueOf(valuePack.getNumMonths()))
                    .path(String.valueOf(valuePack.getLoanAmount()))
                    .path(String.valueOf(valuePack.getInterestRate())).request().get(Estimates.class);
            items = new ListDataModel(estimates.getEstimates());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CreditCalcReceived"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("CreditCalcErrored"));
        }
        return "credit";
    }

    public String sendPost() {
        try {
            Estimates estimates = ClientBuilder.newClient().target(CREDIT_CALC_SITE + version + CREDIT_CALC_SERVICE)
                    .request()
                    .post(Entity.entity(valuePack, MediaType.APPLICATION_XML_TYPE))
                    .readEntity(Estimates.class);
            items = new ListDataModel(estimates.getEstimates());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CreditCalcReceived"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("CreditCalcErrored"));
        }
        return "credit";
    }

}
