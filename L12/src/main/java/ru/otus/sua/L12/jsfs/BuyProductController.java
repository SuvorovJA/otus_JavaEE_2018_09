package ru.otus.sua.L12.jsfs;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L12.ejbs.CartEJB;
import ru.otus.sua.L12.ejbs.OrderEJB;
import ru.otus.sua.L12.ejbs.ProductEJB;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;


@Named
@RequestScoped
@Data
@Slf4j
public class BuyProductController implements Serializable {

    @EJB
    private ProductEJB productEJB;

    private Integer amount;

    public String doBuyProduct(Long productId, CartEJB cartEJB) {
        log.info("Add product id={} in amount={} to cart.", productId, amount);
        cartEJB.addOrUpdateProduct(productEJB.findProductById(productId),amount);
        return "viewProducts.xhtml";
    }

}