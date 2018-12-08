package ru.otus.sua.L11.addressbookService;

import lombok.Data;
import ru.otus.sua.L11.entity.Contact;
import ru.otus.sua.L11.entity.Contacts;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Field;
import java.util.List;


@Data
@ApplicationScoped
public class RestEntityManager implements RestEntityManagerInterface {

    private static final String ADDRESSBOOK_SERVICE = "http://localhost:8080/L11-jaxrs-service_Web/addressbook/crud";

    @Override
    public <T> void persist(T entity) {
        ClientBuilder.newClient().target(ADDRESSBOOK_SERVICE)
                .request()
                .post(Entity.entity(entity, MediaType.APPLICATION_XML), entity.getClass());
    }

    @Override
    public <T> T merge(T entity) {
        Object returned = ClientBuilder.newClient().target(ADDRESSBOOK_SERVICE)
                .request()
                .put(Entity.entity(entity, MediaType.APPLICATION_XML), entity.getClass());
        return (T) returned;
    }

    @Override
    public <T> void remove(T entity) {
        // TODO how to do this by T ?
        Contact contact = (Contact) entity;
        remove(contact.getId());
    }

    @Override
    public <T> void remove(Long id) {
        ClientBuilder.newClient().target(ADDRESSBOOK_SERVICE)
                .path(String.valueOf(id))
                .request()
                .delete();
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        return ClientBuilder.newClient().target(ADDRESSBOOK_SERVICE)
                .path(String.valueOf(id))
                .request()
                .get(clazz);
    }

    @Override
    public <T> List<T> findAll() {
        Contacts contacts = ClientBuilder.newClient().target(ADDRESSBOOK_SERVICE)
                .request()
                .get(Contacts.class);
        return (List<T>) contacts.getContacts();
    }

    @Override
    public <T> List<T> findRange(int[] range) {
        return findAll();
    }
}
