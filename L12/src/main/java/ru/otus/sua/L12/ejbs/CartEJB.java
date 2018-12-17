package ru.otus.sua.L12.ejbs;

import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L12.entities.Customer;
import ru.otus.sua.L12.entities.Order;
import ru.otus.sua.L12.entities.Product;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Stateful
@SessionScoped
@Named
@Slf4j
public class CartEJB implements Serializable {

    private Map<Product, Integer> cart;

    private Customer customer;

    @PostConstruct
    private void init() {
        cart = new ConcurrentHashMap<>();
        customer = new Customer();
    }

    public void addOrUpdateProduct(Product product, Integer amount) {
        if (cart.containsKey(product)) {
            int newAmount = cart.get(product) + amount;
            if (newAmount <= 0) {
                cart.remove(product);
            } else {
                cart.put(product, newAmount);
            }
        } else {
            cart.put(product, amount);
        }
        log.info("Product '{}' added to cart '{}' in amount '{}'", product, getCartId(), amount);
        log.info("Total products ={}", getProductsCountInCart());
        log.info("Total price of products ={}", getPriceSummInCart());
    }

    public int getProductsCountInCart() {
        return cart.size();
    }

    public double getPriceSummInCart() {
        double sum = 0;
        for (Product product : cart.keySet()) {
            sum += product.getPrice() * cart.get(product);
        }
        return sum;
    }

    public Order getNewOrder() {
        Order order = new Order();
        order.setCreationTime(new Date());
        order.setUpdatedTime(new Date());
        order.setCustomer(customer);
        order.setProducts(cart);
        return order;
    }

    public String getCartId() {
        return this.toString();
    }

    public int getProductAmountInCart(Product product) {
        return cart.getOrDefault(product, 0);
    }

}
