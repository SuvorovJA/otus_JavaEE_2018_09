package ru.otus.sua.entities;

import lombok.Data;

import java.util.ArrayList;

@Data
//@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
public class Employes {

    //    @XmlElementWrapper(name = "employes-list")
//    @XmlElement(name = "employee")
    private ArrayList<EmployeEntity> employes;
}

