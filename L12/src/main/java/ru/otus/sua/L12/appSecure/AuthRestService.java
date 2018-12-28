package ru.otus.sua.L12.appSecure;

import lombok.extern.slf4j.Slf4j;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

import static javax.security.enterprise.AuthenticationStatus.SUCCESS;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;

//import static javax.security.auth.message.AuthStatus.SEND_FAILURE;
//import javax.ws.rs.core.SecurityContext;

@Stateless
@Slf4j
@Path("/auth")
@Produces({MediaType.APPLICATION_JSON})
public class AuthRestService {

    @Inject
    private SecurityContext enterpriseSecurityContext;

    @GET
    @Path("logout/{user}")
    public Response code(@Context HttpServletRequest request,
                         @PathParam("user") String user) throws ServletException {
        log.info("REST logout: {}", user);
        request.logout();
        return Response.ok("logged out: " +
                Objects.toString(
                        enterpriseSecurityContext.getCallerPrincipal(),
                        "no principal"))
                .build();
    }

    @GET
    @Path("login/{user}/{pass}")
    public Response auth(
            @Context HttpServletRequest request,
            @Context HttpServletResponse response,
            @PathParam("user") String user,
            @PathParam("pass") String pass
    ) {
        log.info("REST login: {}", user);
        if (user == null || pass == null) return Response.noContent().build();
        if (request == null || response == null) return Response.serverError().build();

        AuthenticationStatus status = enterpriseSecurityContext.authenticate(
                request,
                response,
                withParams().credential(new UsernamePasswordCredential(user, pass)
                ));

        if (status.equals(SUCCESS)) {
            return Response.ok("logged in: " + status + " as '" + enterpriseSecurityContext.getCallerPrincipal().getName() + "'").build();
        } else {
            return Response.ok("logged in: " + status).build();
        }
    }
}
