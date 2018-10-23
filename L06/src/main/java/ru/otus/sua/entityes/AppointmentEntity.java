package ru.otus.sua.entityes;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "appointments")
//@XmlRootElement(name = "appointment")
//@XmlAccessorType(XmlAccessType.FIELD)
public class AppointmentEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "appointment_id")
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

}
