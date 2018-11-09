package ru.otus.sua.L07.entities;

import lombok.Data;
import org.hibernate.annotations.NaturalId;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "departs")
@XmlRootElement(name = "departament")
@XmlAccessorType(XmlAccessType.FIELD)
public class DepartamentEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "depart_id")
    @XmlAttribute(required = true)
    private long id;

    @Basic
    @Column(nullable = false, unique = true)
    @NaturalId
    @XmlElement(required = true)
    @Field
    private String name;

    @Override
    public String toString() {
        return "{ id:" + id + "; name:" + name + "}";
    }

}
