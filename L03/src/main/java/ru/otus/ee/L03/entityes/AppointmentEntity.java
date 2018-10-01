package ru.otus.ee.L03.entityes;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;


@Data
@Entity
@Table(name = "APPOINTMENTS")
public class AppointmentEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "appointment_id")
    private long id;

    @Basic
    private String name;
    
}
