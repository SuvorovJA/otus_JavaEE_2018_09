package ru.otus.sua.L12.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "ORDERS")
@NamedQuery(name = "Order.findAllOrders", query = "SELECT o FROM Order o")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection
    private Map<Product, Integer> products = new HashMap<>();

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date creationTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Basic
    private String address;
}
