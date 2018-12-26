package ru.otus.sua.L12.appSecure;

import lombok.extern.slf4j.Slf4j;
import org.omnifaces.filter.HttpFilter;
import org.omnifaces.util.Servlets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@WebFilter(urlPatterns = {"/*"})
@Slf4j
public class AuthenticationHttpFilter extends HttpFilter {

    // list URL that want to exclude from authentication filter
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/login.xhtml", "/register.xhtml", "/index.html", "/viewProducts.xhtml")
    ));


    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, HttpSession session, FilterChain chain) throws ServletException, IOException {

        String loginUrl = req.getContextPath() + "/login.xhtml";

        String path = req.getRequestURI().substring(req.getContextPath().length()).replaceAll("[/]+$", "");


        // check if user already logged or not, session for authenticate user was on this getRemoteUser()
        boolean loggedIn = (req.getRemoteUser() != null);

        // check if the URL was appointed to login URL or not
        boolean loginRequest = req.getRequestURI().equals(loginUrl);

        // images & etc ?
        boolean resourceRequest = Servlets.isFacesResourceRequest(req);

        // check if the URL was allowed or not
        boolean allowedUrl = ALLOWED_PATHS.contains(path);

        log.info("login url: {}, path:{}, allowed:{}",loginUrl,path,allowedUrl);

        if (loggedIn || loginRequest || resourceRequest || allowedUrl) {
            // Prevent browser from caching restricted resources
            if (!resourceRequest) Servlets.setNoCacheHeaders(res);
            log.info("filter passed to path: {}",path);
            chain.doFilter(req, res); // So, just continue request.
        } else {
            Servlets.facesRedirect(req, res, loginUrl);
        }
    }

}
