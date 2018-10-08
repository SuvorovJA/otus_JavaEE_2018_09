package ru.otus.ee.L03.entityes;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Employes {

    @XmlElementWrapper(name = "employes-list")
    @XmlElement(name = "employee")
    private List<String> employes;
}

