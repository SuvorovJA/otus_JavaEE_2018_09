package ru.otus.ee.L03.entityes;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "departs")
public class DepartmentEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "depart_id")
    private long id;

    @Basic
    @Column(nullable = false, unique = true)
    @NaturalId
    private String name;
}
