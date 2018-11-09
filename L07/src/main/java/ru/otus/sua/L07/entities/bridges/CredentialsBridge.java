package ru.otus.sua.L07.entities.bridges;

import org.hibernate.search.bridge.StringBridge;
import ru.otus.sua.L07.entities.CredentialEntity;

public class CredentialsBridge implements StringBridge {
    @Override
    public String objectToString(Object o) {
        if (o instanceof CredentialEntity) {
            CredentialEntity entity = (CredentialEntity) o;
            return entity.getLogin();
        } else if (o instanceof String) {
            return o.toString();
        } else {
            return "";
        }

    }
}
