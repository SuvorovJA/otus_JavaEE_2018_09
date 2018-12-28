package ru.otus.sua.L12.appSecure;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class JaxRsConfig extends Application {

    private final Set<Class<?>> classes;

    public JaxRsConfig() {
        HashSet<Class<?>> c = new HashSet<>();
        c.add(AuthRestService.class);
        classes = Collections.unmodifiableSet(c);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}