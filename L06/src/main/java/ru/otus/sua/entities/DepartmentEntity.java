package ru.otus.sua.entities;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "departs")
//@XmlRootElement(name = "departament")
//@XmlAccessorType(XmlAccessType.FIELD)
public class DepartmentEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "depart_id")
//    @XmlAttribute(required = true)
    private long id;

    @Basic
    @Column(nullable = false, unique = true)
    @NaturalId
//    @XmlElement(required = true)
    private String name;

    @Override
    public String toString() {
        return "{ id:" + id + "; name:" + name + "}";
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
