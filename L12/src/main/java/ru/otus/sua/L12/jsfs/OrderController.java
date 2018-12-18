package ru.otus.sua.L12.jsfs;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L12.ejbs.CartEJB;
import ru.otus.sua.L12.ejbs.OrderEJB;
import ru.otus.sua.L12.ejbs.OrderRemoteMonEJB;
import ru.otus.sua.L12.entities.Customer;
import ru.otus.sua.L12.entities.Order;
import ru.otus.sua.L12.entities.Product;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Named
@RequestScoped
@Data
@Slf4j
public class OrderController implements Serializable {

    @EJB
    private OrderEJB orderEJB;

    @EJB
    private OrderRemoteMonEJB remoteMonEJB;

    private Order order;

    @PostConstruct
    private void init() {
        order = new Order();
    }

    public String doOrdering(CartEJB cartEJB) {
        log.info("New order issue for cart '{}'", cartEJB.toString());
        Order order = cartEJB.getNewOrder();
        orderEJB.createOrder(order);
        remoteMonEJB.setLastOrder(order);
        cartEJB.init();
        return "viewOrders.xhtml";
    }

    public double getPriceSummInOrder(Long id) {
        double sum = 0;
        Order order = orderEJB.findOrderById(id);
        if (order == null) return 0;
        for (Product product : order.getProducts().keySet()) {
            sum += product.getPrice() * order.getProducts().get(product);
        }
        return sum;
    }

    public Set<Product> getOrderProducts(){
        return order.getProducts().keySet();
    }

    public void doFindOrderById() {
        order = orderEJB.findOrderById(order.getId());
//        for (Product product : order.getProducts().keySet()) {
//            log.info("id={} amount={}",product.getId(),order.getProducts().get(product));
//        }
    }

}