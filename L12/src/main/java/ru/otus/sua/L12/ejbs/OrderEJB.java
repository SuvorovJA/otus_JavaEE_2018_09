package ru.otus.sua.L12.ejbs;

import ru.otus.sua.L12.entities.Order;
import ru.otus.sua.L12.entities.Product;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@Named
public class OrderEJB {

    @PersistenceContext(unitName = "shopsimulator-persistens-unit")
    private EntityManager em;

    public List<Order> findAllOrders() {
        TypedQuery<Order> query = em.createNamedQuery("Order.findAllOrders", Order.class);
        return query.getResultList();
    }

    public Order findOrderById(Long id) {
        return em.find(Order.class, id);
    }

    public Order createOrder(Order order) {
        em.persist(order);
        return order;
    }

    public void deleteOrder(Order order) {
        em.remove(em.merge(order));
    }

    public Order updateOrder(Order order) {
        return em.merge(order);
    }

    public void flush() {
        em.flush();
    }



}
