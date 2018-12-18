package ru.otus.sua.L12.ejbs;

import lombok.Data;
import ru.otus.sua.L12.entities.Order;
import ru.otus.sua.L12.entities.Product;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;

@Singleton
@ApplicationScoped
@Remote(OrderRemote.class)
@LocalBean
public class OrderRemoteMonEJB implements OrderRemote {

    private Order lastOrder;

    public void setLastOrder(Order order){
        synchronized (this) {
            lastOrder = order;
        }
    }

    @Override
    public String getLastOrder(){

        if (lastOrder == null) return "none orders";

        double sum = 0;
        for (Product product : lastOrder.getProducts().keySet()) {
            sum += product.getPrice() * lastOrder.getProducts().get(product);
        }

        return "id: "+ lastOrder.getId() +
                "; customer: " + lastOrder.getCustomer().getName() +
                "; address: " + lastOrder.getCustomer().getAddress() +
                "; total summ: " + sum;
    }
}
