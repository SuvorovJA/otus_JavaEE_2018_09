package ru.otus.sua.L12.ejbs;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.omnifaces.util.Utils;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

@Startup
@Singleton
@Data
@Slf4j
public class ResourceImage implements Serializable {

    private byte[] placeholderImage;

    @PostConstruct
    public void init() {
        // TODO "Warning:(23, 9) Usage of 'java.lang.ClassLoader' is not allowed in EJB" but work
        ClassLoader loader = ResourceImage.class.getClassLoader();
        InputStream in = loader.getResourceAsStream("img/placeholder.jpg");
        try {
            placeholderImage = Utils.toByteArray(in);
        } catch (IOException e) {
            log.warn("Cant init placeholder image");
            placeholderImage = new byte[1];
        }
        log.info("Placeholder image ={} bytes.", placeholderImage.length);
    }

}
