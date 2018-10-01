package ru.otus.ee.L03.entityes;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "appointments")
public class AppointmentEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "appointment_id")
    private long id;

    @Basic
    @Column(nullable = false, unique = true)
    @NaturalId
    private String name;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ id:").append(id).append("; name:").append(name).append("}");
        return sb.toString();
    }

}
