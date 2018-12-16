package ru.otus.sua.L12.ejbs;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Startup
@Singleton
@Data
@Slf4j
public class ResourceImage {

    private byte[] placeholderImage;

    @PostConstruct
    public void init() {
        // TODO "Warning:(23, 9) Usage of 'java.lang.ClassLoader' is not allowed in EJB" but work
        ClassLoader loader = ResourceImage.class.getClassLoader();
        InputStream in = loader.getResourceAsStream("img/placeholder.jpg");
        try {
            placeholderImage = getBytesFromInputStream(in);
        } catch (IOException e) {
            log.warn("Cant init placeholder image");
            placeholderImage = new byte[1];
        }
        log.info("Placeholder image ={} bytes.", placeholderImage.length);
    }


    private byte[] getBytesFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[0xFFFF];
        for (int len = is.read(buffer); len != -1; len = is.read(buffer)) {
            os.write(buffer, 0, len);
        }
        return os.toByteArray();
    }


}
