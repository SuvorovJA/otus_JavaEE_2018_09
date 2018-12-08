package ru.otus.sua.L11.ejb;

import ru.otus.sua.L11.entity.Contact;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ContactFacade extends AbstractFacade<Contact> {

    @Inject
    private RestEntityManager em;

    public ContactFacade() {
        super(Contact.class);
    }

    @Override
    protected RestEntityManager getEntityManager() {
        return em;
    }

}
