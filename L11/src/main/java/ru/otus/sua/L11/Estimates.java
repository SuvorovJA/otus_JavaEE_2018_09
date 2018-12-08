package ru.otus.sua.L11;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@XmlRootElement
@XmlSeeAlso(Double.class)
public class Estimates extends ArrayList<Double> {

    public Estimates() {
        super();
    }

    public Estimates(Collection<? extends Double> c) {
        super(c);
    }

    @XmlElement(name = "MonthlyPayment")
    public List<Double> getEstimates() {
        return this;
    }

    public void setEstimates(List<Double> estimates) {
        this.addAll(estimates);
    }
}