package ru.otus.sua.L11.addressbookService;

import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L11.entity.Contact;
import ru.otus.sua.L11.entity.Contacts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;


@Slf4j
@Path("/crud")
@Produces({MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_XML})
@Stateless
public class AddressbookService {

    @Inject
    private ContactFacade cf;
    
    @Context
    private UriInfo uriInfo;

    @POST
    public Response createContact(Contact contact) {
        if (contact == null) throw new BadRequestException();
        log.info("CREATE: {}",contact);
        cf.getEM().persist(contact);
        URI bookUri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(contact.getId())).build();
        return Response.created(bookUri).build();
    }

    @PUT
    public Response updateContact(Contact contact) {
        if (contact == null) throw new BadRequestException();
        log.info("UPDATE: {}",contact);
        cf.getEM().merge(contact);
        return Response.ok().build();
    }

    @GET
    @Path("{id}")
    public Response getContact(@PathParam("id") String id) {
        log.info("GET: {}",id);
        Contact contact = cf.getEM().find(Contact.class, Long.parseLong(id));
        if (contact == null) throw new NotFoundException();
        return Response.ok(contact).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteContact(@PathParam("id") String id) {
        log.info("DELETE: {}",id);
        Contact contact = cf.getEM().find(Contact.class, Long.parseLong(id));
        if (contact == null) throw new NotFoundException();
        cf.getEM().remove(contact);
        return Response.noContent().build();
    }

    @GET
    public Response getAllContacts() {
        log.info("GETALL: ");
        TypedQuery<Contact> query = cf.getEM().createNamedQuery(Contact.FIND_ALL, Contact.class);
        Contacts books = new Contacts(query.getResultList());
        return Response.ok(books).build();
    }
}
