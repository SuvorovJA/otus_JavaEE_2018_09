package ru.otus.sua.L07.entities.bridges;

import org.hibernate.search.bridge.StringBridge;
import ru.otus.sua.L07.entities.DepartamentEntity;

public class DepartamentBridge implements StringBridge {
    @Override
    public String objectToString(Object o) {
        if (o instanceof DepartamentEntity) {
            DepartamentEntity entity = (DepartamentEntity) o;
            return entity.getName();
        } else if (o instanceof String) {
            return o.toString();
        } else {
            return "";
        }
    }
}
