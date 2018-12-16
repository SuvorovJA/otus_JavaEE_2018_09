package ru.otus.sua.L12.ejbs;

import ru.otus.sua.L12.entities.Order;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class OrderEJB {

    @PersistenceContext(unitName = "shopsimulator-persistens-unit")
    private EntityManager em;

    public List<Order> findOrders() {
        TypedQuery<Order> query = em.createNamedQuery("Order.findAllOrders", Order.class);
        return query.getResultList();
    }

    public Order findOrderById(Long id) {
        return em.find(Order.class, id);
    }

    public Order createProduct(Order order) {
        em.persist(order);
        return order;
    }

    public void deleteProduct(Order order) {
        em.remove(em.merge(order));
    }

    public Order updateProduct(Order order) {
        return em.merge(order);
    }

    public void flush() {
        em.flush();
    }

}
