package ru.otus.sua.L10;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.jws.WebMethod;
import javax.jws.WebService;

@NoArgsConstructor
@Slf4j
@WebService(endpointInterface = "ru.otus.sua.L10.Estimator")
public class EstimateIncomeTax implements Estimator {

    @Override
    public double estimateIncomeTax(TaxData taxData) {
        log.info("received data for tax estimating: {}", taxData);
        return estimate(taxData);

    }

    @WebMethod(exclude = true)
    public double estimate(TaxData taxData) {
        IncomeTaxStrategy strategy = new IncomeTaxStrategy() {
        };
        double result = strategy.calculator(taxData.getTaxRate(), taxData.getRevenuesYear(), taxData.getExpenses());
        log.info("result of estimating: {}", result);
        return result;
    }
}
