package ru.otus.sua.L12.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "PRODUCTS")
@NamedQuery(name = "Product.findAllProducts", query = "SELECT p FROM Product p")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    private String name;

    @Basic
    private String description;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date creationTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Lob
    @ToString.Exclude
    private byte[] image;

}
