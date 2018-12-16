package ru.otus.sua.L12.ejbs;

import ru.otus.sua.L12.entities.Product;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Stateless
@Named
public class ProductEJB implements Serializable {

    @PersistenceContext(unitName = "shopsimulator-persistens-unit")
    private EntityManager em;

    public List<Product> findAllProducts() {
        TypedQuery<Product> query = em.createNamedQuery("Product.findAllProducts", Product.class);
        return query.getResultList();
    }

    public Product findProductById(Long id) {
        return em.find(Product.class, id);
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
