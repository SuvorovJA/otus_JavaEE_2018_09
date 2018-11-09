package ru.otus.sua.L07.entities.bridges;

import org.hibernate.search.bridge.StringBridge;
import ru.otus.sua.L07.entities.AppointmentEntity;

public class AppointmentBridge implements StringBridge {
    @Override
    public String objectToString(Object o) {
        if (o instanceof AppointmentEntity) {
            AppointmentEntity entity = (AppointmentEntity) o;
            return entity.getName();
        } else if (o instanceof String) {
            return o.toString();
        } else {
            return "";
        }
    }
}
