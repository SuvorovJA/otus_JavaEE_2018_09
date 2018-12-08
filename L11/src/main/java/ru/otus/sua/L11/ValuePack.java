package ru.otus.sua.L11;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@XmlSeeAlso({Double.class, Integer.class})
public class ValuePack {
    private Integer numMonths;
    private Double loanAmount;
    private Double interestRate;
}
