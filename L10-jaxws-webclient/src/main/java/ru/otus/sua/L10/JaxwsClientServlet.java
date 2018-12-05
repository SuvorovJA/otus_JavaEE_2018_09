package ru.otus.sua.L10;

import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L10.jaxws.EstimateIncomeTaxService;
import ru.otus.sua.L10.jaxws.Estimator;
import ru.otus.sua.L10.jaxws.TaxData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import javax.xml.ws.soap.SOAPFaultException;
import java.io.IOException;

@Slf4j
@WebServlet(name = "JaxwsClientServlet", urlPatterns = "/taxEstimate")
public class JaxwsClientServlet extends HttpServlet {

    @WebServiceRef(wsdlLocation = "http://localhost:8080/L10/EstimateIncomeTaxService?WSDL")
    private EstimateIncomeTaxService service;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            TaxData taxData = new TaxData();
            taxData.setRevenuesYear(validateAndParse(request.getParameter("revenues")));
            taxData.setExpenses(validateAndParse(request.getParameter("expenses")));
            taxData.setTaxRate(validateAndParse(request.getParameter("taxRate")));
            Estimator estimator = service.getEstimateIncomeTaxPort();
            double result = estimator.estimateIncomeTax(taxData);
            request.setAttribute("incomeTax", result);
        } catch (NumberFormatException | SOAPFaultException e) {
            request.setAttribute("incomeTax", "<strong>ARG ERR: </strong>" + e.getMessage());
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    private double validateAndParse(String param) throws NumberFormatException {
        if (param == null) throw new NumberFormatException("not set");
        double result = Double.parseDouble(param);
        if (result < 0) throw new NumberFormatException("negative");
        return result;
    }

}
