package ru.otus.sua.L12.appSecure.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


@Entity
@Table(name = "ROLES")
@NamedQuery(name = "Role.findAllRoles", query = "SELECT r FROM Role r")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    private String role;

    @ManyToOne(optional = false)
    @EqualsAndHashCode.Exclude
    private Account account;

}