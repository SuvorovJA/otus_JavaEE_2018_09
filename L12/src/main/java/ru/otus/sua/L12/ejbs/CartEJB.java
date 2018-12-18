package ru.otus.sua.L12.ejbs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L12.entities.Customer;
import ru.otus.sua.L12.entities.Order;
import ru.otus.sua.L12.entities.Product;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
@Setter
@Getter
public class CartEJB implements Serializable {

    @EJB
    private CustomerEJB customerEJB;

    private Map<Product, Integer> cart;

    private Customer customer;

    @PostConstruct
    public void init() {
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
        order.setProducts(cart);

        // TODO Internal Exception: org.postgresql.util.PSQLException: ERROR: duplicate key value violates unique constraint "customers_pkey"
        //  Detail: Key (id)=(252) already exists.
        //  Error Code: 0
        //  Call: INSERT INTO CUSTOMERS (ID, ADDRESS, NAME) VALUES (?, ?, ?)
        //	bind => [3 parameters bound]
        //  Query: InsertObjectQuery(Customer(id=252, name=Том Д. Хоум, address=Австралия, далеко.))

//        if (customerEJB.contains(customer)) {
//            customer = customerEJB.findCustomerById(customer.getId());
//        }

        customer.addOrderToHistory(order);
        order.setCustomer(customer);
        return order;
    }

    public String getCartId() {
        return this.toString();
    }

    public int getProductAmountInCart(Product product) {
        return cart.getOrDefault(product, 0);
    }

}
