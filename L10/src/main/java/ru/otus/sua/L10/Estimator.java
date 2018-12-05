package ru.otus.sua.L10;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface Estimator {

    @WebMethod
    public double estimateIncomeTax(@WebParam(name = "EstimatingData") TaxData estimatingData);

}
