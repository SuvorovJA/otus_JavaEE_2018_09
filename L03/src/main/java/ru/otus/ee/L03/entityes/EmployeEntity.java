package ru.otus.ee.L03.entityes;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "employes")
public class EmployeEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Basic
    private String fullname;

    @Basic
    private String city;

    @Basic
    private long salary;

    @ManyToOne
    @JoinColumn(name = "depart_id", referencedColumnName = "depart_id")
    private DepartmentEntity department;

    @ManyToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "appointment_id")
    private AppointmentEntity appointment;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "credentials_id")
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
