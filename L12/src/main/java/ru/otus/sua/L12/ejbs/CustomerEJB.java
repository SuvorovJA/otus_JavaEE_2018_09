package ru.otus.sua.L12.ejbs;

import ru.otus.sua.L12.entities.Customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class CustomerEJB {

    @PersistenceContext(unitName = "shopsimulator-persistens-unit")
    private EntityManager em;

    public List<Customer> findCustomers() {
        TypedQuery<Customer> query = em.createNamedQuery("Customer.findAllCustomers", Customer.class);
        return query.getResultList();
    }

    public Customer findCustomerById(Long id) {
        return em.find(Customer.class, id);
    }

    public Customer createCustomer(Customer customer) {
        em.persist(customer);
        return customer;
    }

    public void deleteOrder(Customer customer) {
        em.remove(em.merge(customer));
    }

    public Customer updateOrder(Customer customer) {
        return em.merge(customer);
    }

    public void flush() {
        em.flush();
    }

    public boolean contains(Customer customer){
        return em.contains(customer);
    }

}
