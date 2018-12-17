package ru.otus.sua.L12.jsfs;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L12.ejbs.CartEJB;
import ru.otus.sua.L12.ejbs.OrderEJB;
import ru.otus.sua.L12.entities.Order;
import ru.otus.sua.L12.entities.Product;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;


@Named
@RequestScoped
@Data
@Slf4j
public class OrderController implements Serializable {

    @EJB
    private OrderEJB orderEJB;

    public String doOrdering(CartEJB cartEJB) {
        log.info("New order issue for cart '{}'", cartEJB.toString());
        orderEJB.createOrder(cartEJB.getNewOrder());
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
}