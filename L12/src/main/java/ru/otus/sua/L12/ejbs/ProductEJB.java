package ru.otus.sua.L12.ejbs;

import ru.otus.sua.L12.entities.Product;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class ProductEJB {

    @PersistenceContext(unitName = "shopsimulator-persistens-unit")
    private EntityManager em;

    public List<Product> findProducts() {
        TypedQuery<Product> query = em.createNamedQuery("Product.findAllProducts", Product.class);
        return query.getResultList();
    }

    public Product createProduct(Product product) {
        em.persist(product);
        return product;
    }

    public void deleteProduct(Product product) {
        em.remove(em.merge(product));
    }

    public Product updateProduct(Product product) {
        return em.merge(product);
    }

    public void flush() {
        em.flush();
    }

}
