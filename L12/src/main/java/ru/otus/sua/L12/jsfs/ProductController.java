package ru.otus.sua.L12.jsfs;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L12.ejbs.ProductEJB;
import ru.otus.sua.L12.ejbs.ResourceImage;
import ru.otus.sua.L12.ejbs.UploadImageBean;
import ru.otus.sua.L12.entities.Product;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;


@Named
@RequestScoped
@Data
@Slf4j
public class ProductController implements Serializable {

    @Inject
    private ProductEJB productEJB;

    @Inject
    private ResourceImage resourceImage;

    @Inject
    private UploadImageBean uploadImage;

    private Product product;

    @PostConstruct
    private void init() {
        product = new Product();
        product.setCreationTime(new Date());
        product.setUpdatedTime(new Date());
        product.setImage(resourceImage.getPlaceholderImage());
    }

    public String doCreateProduct() {
        if (uploadImage.isUploaded())
            product.setImage(uploadImage.getContentAndReset());

        log.info("Persisted image ={} bytes.", product.getImage().length);
        productEJB.createProduct(product);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Product created",
                        "The product" + product.getName() +
                                " has been created with id=" + product.getId()));
        return "newProduct.xhtml";
    }

    public void doFindProductById() {
        product = productEJB.findProductById(product.getId());
    }

}