package ru.otus.sua.L11;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L11.addressbookService.ContactFacade;
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

@Api(tags = "Addressbook CRUD")
@SwaggerDefinition(
        info = @Info(
                title = "Swagger-generated RESTful API",
                description = "RESTful Description",
                version = "1.0.0",
                termsOfService = "education",
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org")),
        tags = {
                @Tag(name = "Addressbook CRUD", description = "RESTful API to Addressbook CRUD operations."),
        },
        host = "localhost:8080",
        basePath = "/L11-jaxrs-service_Web",
        schemes = {SwaggerDefinition.Scheme.HTTP}
)
@Slf4j
@Path("/addressbook/crud")
@Produces({MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_XML})
@Stateless
public class AddressbookService {

    @Inject
    private ContactFacade cf;

    @Context
    private UriInfo uriInfo;

    @POST
    @ApiOperation("Create Contact")
    public Response createContact(Contact contact) {
        if (contact == null) throw new BadRequestException();
        log.info("CREATE: {}", contact);
        cf.getEM().persist(contact);
        URI bookUri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(contact.getId())).build();
        return Response.created(bookUri).build();
    }

    @PUT
    @ApiOperation("Update Contact")
    public Response updateContact(Contact contact) {
        if (contact == null) throw new BadRequestException();
        log.info("UPDATE: {}", contact);
        cf.getEM().merge(contact);
        return Response.ok().build();
    }

    @GET
    @ApiOperation("Get Contact by Id")
    @Path("{id}")
    public Response getContact(@PathParam("id") String id) {
        log.info("GET: {}", id);
        Contact contact = cf.getEM().find(Contact.class, Long.parseLong(id));
        if (contact == null) throw new NotFoundException();
        return Response.ok(contact).build();
    }

    @DELETE
    @ApiOperation("Delete Contact by Id")
    @Path("{id}")
    public Response deleteContact(@PathParam("id") String id) {
        log.info("DELETE: {}", id);
        Contact contact = cf.getEM().find(Contact.class, Long.parseLong(id));
        if (contact == null) throw new NotFoundException();
        cf.getEM().remove(contact);
        return Response.noContent().build();
    }

    @GET
    @ApiOperation("Get All Contacts")
    public Response getAllContacts() {
        log.info("GETALL: ");
        TypedQuery<Contact> query = cf.getEM().createNamedQuery(Contact.FIND_ALL, Contact.class);
        Contacts books = new Contacts(query.getResultList());
        return Response.ok(books).build();
    }
}
