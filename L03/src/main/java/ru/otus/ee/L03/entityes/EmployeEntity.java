package ru.otus.ee.L03.entityes;

import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "employes")
@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeEntity implements Serializable {

    @Id
    @GeneratedValue
    @XmlAttribute(required = true)
    private long id;

    @Basic
    @XmlElement(required = true)
    private String fullname;

    @Basic
    @XmlElement(required = true)
    private String city;

    @Basic
    @XmlElement(required = true)
    private long salary;

    @ManyToOne
    @JoinColumn(name = "depart_id", referencedColumnName = "depart_id")
    @XmlElement(required = true)
    private DepartmentEntity department;

    @ManyToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "appointment_id")
    @XmlElement(required = true)
    private AppointmentEntity appointment;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "credentials_id")
    @XmlElement(required = true)
    private CredentialEntity credentials;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ id:").append(id)
                .append("; fullname:").append(fullname)
                .append("; city:").append(city)
                .append("; salary:").append(salary)
                .append("; department:").append(department.toString())
                .append("; appointment:").append(appointment.toString())
                .append("; credentials:").append(credentials.toString())
                .append("}");
        return sb.toString();
    }
}
