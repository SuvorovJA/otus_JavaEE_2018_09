package ru.otus.ee.L03.entityes;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "credentials")
public class CredentialEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(optional = false, mappedBy = "credentials")
    private EmployeEntity employe;

    @Basic
    private String login;

    @Basic
    private String passhash;

}
