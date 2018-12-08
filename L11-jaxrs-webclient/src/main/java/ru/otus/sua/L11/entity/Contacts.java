package ru.otus.sua.L11.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@XmlRootElement
@XmlSeeAlso(Contact.class)
public class Contacts extends ArrayList<Contact> {

    public Contacts() {
        super();
    }

    public Contacts(Collection<? extends Contact> c) {
        super(c);
    }

    @XmlElement(name = "contact")
    public List<Contact> getContacts() {
        return this;
    }

    public void setContacts(List<Contact> contacts) {
        this.addAll(contacts);
    }
}