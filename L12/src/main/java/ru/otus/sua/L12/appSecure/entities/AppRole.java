package ru.otus.sua.L12.appSecure.entities;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "APPLICATION_ROLES")
@NamedQuery(name = "AppRole.findAllAppRoles", query = "SELECT r FROM AppRole r")
@Data
public class AppRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    private String role;

}