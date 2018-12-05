
package ru.otus.sua.L10.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for taxData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="taxData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="revenuesYear" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="expenses" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="taxRate" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "taxData")
public class TaxData {

    @XmlAttribute(name = "revenuesYear", required = true)
    protected double revenuesYear;
    @XmlAttribute(name = "expenses", required = true)
    protected double expenses;
    @XmlAttribute(name = "taxRate", required = true)
    protected double taxRate;

    /**
     * Gets the value of the revenuesYear property.
     * 
     */
    public double getRevenuesYear() {
        return revenuesYear;
    }

    /**
     * Sets the value of the revenuesYear property.
     * 
     */
    public void setRevenuesYear(double value) {
        this.revenuesYear = value;
    }

    /**
     * Gets the value of the expenses property.
     * 
     */
    public double getExpenses() {
        return expenses;
    }

    /**
     * Sets the value of the expenses property.
     * 
     */
    public void setExpenses(double value) {
        this.expenses = value;
    }

    /**
     * Gets the value of the taxRate property.
     * 
     */
    public double getTaxRate() {
        return taxRate;
    }

    /**
     * Sets the value of the taxRate property.
     * 
     */
    public void setTaxRate(double value) {
        this.taxRate = value;
    }

}
