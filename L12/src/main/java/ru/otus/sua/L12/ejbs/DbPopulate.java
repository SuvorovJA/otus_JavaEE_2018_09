package ru.otus.sua.L12.ejbs;

import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L12.entities.Product;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Date;

@Singleton
@Startup
@Slf4j
public class DbPopulate {

    @EJB
    private ResourceImage resourceImage;

    @EJB
    private ProductEJB productEJB;

    @PostConstruct
    private void init() {
        if (productEJB.findProductById(1L) == null) {
            Product product = newProduct();
            productEJB.createProduct(product);
            log.info("Populate database with product: {}", product.toString());
            productEJB.flush();
        }
    }

    private Product newProduct() {
        Product product = new Product();
        product.setName("Arduino Uno");
        product.setCreationTime(new Date());
        product.setUpdatedTime(new Date());
        product.setPrice(10.);
        product.setDescription("Arduino Uno контроллер построен на ATmega328");
        product.setImage(resourceImage.getPlaceholderImage());
        return product;
    }
}
