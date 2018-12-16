package ru.otus.sua.L12.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@Table(name = "PRODUCTS")
@NamedQuery(name = "Product.findAllProducts", query = "SELECT p FROM Product p")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 8, max = 180)
    @Basic
    private String name;

    @Basic
    @NotNull
    private Double price;

    @Basic
    @Column(length = 1000)
    private String description;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date creationTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Lob
    @ToString.Exclude
    private byte[] image;

}
