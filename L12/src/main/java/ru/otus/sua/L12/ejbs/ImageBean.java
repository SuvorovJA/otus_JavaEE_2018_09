package ru.otus.sua.L12.ejbs;

import lombok.extern.slf4j.Slf4j;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;


@Named
@ApplicationScoped
@Slf4j
public class ImageBean {

    @EJB
    private ProductEJB service;

    public byte[] getImage(Long imageId) {
        byte[] bytes = service.findProductById(imageId).getImage();
        log.info("Get image from product id={}, size ={} bytes.",imageId,bytes.length);
        return bytes;
    }

}