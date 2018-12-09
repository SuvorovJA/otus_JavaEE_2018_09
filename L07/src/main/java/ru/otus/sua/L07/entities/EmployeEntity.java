package ru.otus.sua.L07.entities;

import lombok.Data;
import org.hibernate.search.annotations.*;
import ru.otus.sua.L07.entities.bridges.AppointmentBridge;
import ru.otus.sua.L07.entities.bridges.CredentialsBridge;
import ru.otus.sua.L07.entities.bridges.DepartamentBridge;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
@Indexed
@Table(name = "employes")
@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQuery(name = EmployeEntity.AVG_SALARY, query = "SELECT AVG(e.salary) FROM EmployeEntity e")
public class EmployeEntity implements Serializable {

    public static final String AVG_SALARY = "EmployeEntity.avgSalary";

    @Id
    @GeneratedValue
    @XmlAttribute(required = true)
    private long id;

    @Basic
    @XmlElement(required = true)
    @Field
    private String fullName;

    @Temporal(TemporalType.DATE)
    @XmlElement(required = true)
    @Field
    @DateBridge(resolution= Resolution.DAY)
    private Date dateOfBirth;

    @Basic
    @XmlElement(required = true)
    @Field
    private String city;

    @Basic
    @XmlElement(required = true)
    @Field
    private long salary;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "depart_id", referencedColumnName = "depart_id")
    @XmlElement(required = true)
    @Field(name = "departament")
    @FieldBridge(impl = DepartamentBridge.class)
    private DepartamentEntity departament;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointment_id", referencedColumnName = "appointment_id")
    @XmlElement(required = true)
    @Field(name = "appointment")
    @FieldBridge(impl = AppointmentBridge.class)
    private AppointmentEntity appointment;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "credentials_id")
    @XmlElement(required = true)
    @Field(name="login")
    @FieldBridge(impl = CredentialsBridge.class)
    private CredentialEntity credentials;

    @Override
    public String toString() {
        return "{ id:" + id +
                "; fullname:" + fullName +
                "; birthdate:" + new SimpleDateFormat("dd.MM.yyyy").format(dateOfBirth) +
                "; city:" + city +
                "; salary:" + salary +
                "; departament:" + departament.toString() +
                "; appointment:" + appointment.toString() +
                "; credentials:" + credentials.toString() +
                "}";
    }

}
