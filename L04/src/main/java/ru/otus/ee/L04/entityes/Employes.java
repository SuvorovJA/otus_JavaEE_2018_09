package ru.otus.ee.L04.entityes;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Employes {

    @XmlElementWrapper(name = "employes-list")
    @XmlElement(name = "employee")
    private ArrayList<EmployeEntity> employes;
}

