package ru.otus.sua.L10;

import static org.junit.jupiter.api.Assertions.*;

class EstimateIncomeTaxTest {

    @org.junit.jupiter.api.Test
    void estimateIncomeTax() {
        TaxData taxData = new TaxData(1000000.0,200000.0,20.0);
        EstimateIncomeTax estimator = new EstimateIncomeTax();
        assertEquals(160000.0, estimator.estimateIncomeTax(taxData));
    }
}