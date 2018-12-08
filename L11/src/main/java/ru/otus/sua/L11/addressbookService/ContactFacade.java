package ru.otus.sua.L11.addressbookService;


import ru.otus.sua.L11.entity.Contact;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ContactFacade extends AbstractFacade<Contact> {
    @PersistenceContext(unitName = "address-bookPU")
    private EntityManager em;

    public ContactFacade() {
        super(Contact.class);
    }

    @Override
    public EntityManager getEM() {
        return em;
    }

}
