package ru.otus.sua.L10;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TaxData {
    //До – доходы (от реализации и внереализационные) нарастающим итогом с начала года
    @XmlAttribute(required = true)
    private double revenuesYear;
    //Ро – расходы (связанные с производством и реализацией и внереализационные)
    @XmlAttribute(required = true)
    private double expenses;
    //Нс - налоговая ставка отчетного периода (год);
    @XmlAttribute(required = true)
    private double taxRate;
}
