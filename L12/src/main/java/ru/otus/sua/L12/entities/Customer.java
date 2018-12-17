package ru.otus.sua.L12.entities;

import lombok.Data;
import lombok.ToString;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CUSTOMERS")
@NamedQuery(name = "Customer.findAllCustomers", query = "SELECT c FROM Customer c")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 8, max = 180)
    @Basic
    private String name;

    @NotNull
    @Size(min = 8, max = 180)
    @Basic
    private String address;

    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    private List<Order> orderHistory;

    public void addOrderToHistory(Order order) {
        if (orderHistory == null) orderHistory = new ArrayList<>();
        orderHistory.add(order);
    }

}
