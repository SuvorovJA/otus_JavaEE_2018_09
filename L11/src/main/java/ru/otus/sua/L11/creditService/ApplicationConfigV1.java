package ru.otus.sua.L11.creditService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("v1")
public class ApplicationConfigV1 extends Application {

    private final Set<Class<?>> classes;

    public ApplicationConfigV1() {
        HashSet<Class<?>> c = new HashSet<>();
        c.add(MonthlyPaymentServiceV1.class);
        classes = Collections.unmodifiableSet(c);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}