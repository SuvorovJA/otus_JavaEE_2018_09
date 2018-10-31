package ru.otus.sua.entityes;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "employes")
//@XmlRootElement(name = "employee")
//@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeEntity implements Serializable {

    @Id
    @GeneratedValue
//    @XmlAttribute(required = true)
    private long id;

    @Basic
//    @XmlElement(required = true)
    private String fullname;

    @Basic
//    @XmlElement(required = true)
    private String city;

    @Basic
//    @XmlElement(required = true)
    private long salary;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "depart_id", referencedColumnName = "depart_id")
//    @XmlElement(required = true)
    private DepartmentEntity department;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointment_id", referencedColumnName = "appointment_id")
//    @XmlElement(required = true)
    private AppointmentEntity appointment;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "credentials_id")
//    @XmlElement(required = true)
    private CredentialEntity credentials;


    @Override
    public String toString() {
        return "{ id:" + id +
                "; fullname:" + fullname +
                "; city:" + city +
                "; salary:" + salary +
                "; department:" + department.toString() +
                "; appointment:" + appointment.toString() +
                "; credentials:" + credentials.toString() +
                "}";
    }


    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
