package ru.otus.sua.L11;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("v2")
public class ApplicationConfigV2 extends Application {

    private final Set<Class<?>> classes;

    public ApplicationConfigV2() {
        HashSet<Class<?>> c = new HashSet<>();
        c.add(MonthlyPaymentServiceV2.class);
        classes = Collections.unmodifiableSet(c);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}