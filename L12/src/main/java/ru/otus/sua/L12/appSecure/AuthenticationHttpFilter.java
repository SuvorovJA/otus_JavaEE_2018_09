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
            Arrays.asList("/tfa.xhtml", "/login.xhtml", "/register.xhtml", "/index.html", "/viewProducts.xhtml", "/logout.xhtml", "/loginError.xhtml")
    ));

    private static final Set<String> ADMIN_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/usersAndRoles.xhtml", "/addRoleToAccount.xhtml","/modTfaToAccount.xhtml")
    ));

    private static final Set<String> MANAGER_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/viewOrders.xhtml", "/newProduct.xhtml")
    ));

    private static final Set<String> CUSTOMER_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/viewOrder.xhtml", "/viewProduct.xhtml", "/buyProduct.xhtml","/orderProducts.xhtml")
    ));

    private static final Set<String> REMOTE_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/remote")
    ));

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, HttpSession session, FilterChain chain) throws ServletException, IOException {

        String loginUrl = req.getContextPath() + "/login.xhtml";
        String tfaUrl = req.getContextPath() + "/tfa.xhtml";
        String restAuthUrl = req.getContextPath() + "/auth";

        String path = req.getRequestURI().substring(req.getContextPath().length()).replaceAll("[/]+$", "");

        // check if user already logged or not, session for authenticate user was on this getRemoteUser()
        boolean loggedIn = (req.getRemoteUser() != null);

        // check if the URL was appointed to login URL or not
        boolean loginRequest = (req.getRequestURI().equals(loginUrl) ||
                req.getRequestURI().equals(tfaUrl) ||
                req.getRequestURI().startsWith(restAuthUrl));

        // jsf resources
        boolean resourceRequest = Servlets.isFacesResourceRequest(req);

        boolean isManager = req.isUserInRole("MANAGER");
        boolean isAdmin = req.isUserInRole("ADMIN");
        boolean isCustomer = req.isUserInRole("CUSTOMER");
        boolean isRemote = req.isUserInRole("REMOTE");


        // check if the URL was allowed or not
        boolean allowedUrl = ALLOWED_PATHS.contains(path);
        boolean adminUrl = ADMIN_PATHS.contains(path);
        boolean managerUrl = MANAGER_PATHS.contains(path);
        boolean customerUrl = CUSTOMER_PATHS.contains(path);
        boolean remoteUrl = REMOTE_PATHS.contains(path);

//         log.info("loginurl: {}, path:{}, " +
//                         "url(allowed:{}, customer:{}, manager:{}, admin:{}, remote:{}), " +
//                         "auth(isCustomer:{}, isManager:{}, isAdmin:{}, isRemote:{})",
//                 loginUrl,path,
//                 allowedUrl,customerUrl,managerUrl,adminUrl,remoteUrl,
//                 isCustomer,isManager,isAdmin,isRemote);

        if ((loginRequest || resourceRequest || allowedUrl) ||
                (loggedIn && (adminUrl || managerUrl || customerUrl) && isAdmin) ||
                (loggedIn && (managerUrl || customerUrl) && isManager) ||
                (loggedIn && (customerUrl) && isCustomer) ||
                (loggedIn && (remoteUrl) && isRemote)
        ) {
            // Prevent browser from caching restricted resources
            if (!resourceRequest) Servlets.setNoCacheHeaders(res);
//             log.info("filter passed to path: {}",path);
            chain.doFilter(req, res); // So, just continue request.
        } else {
            Servlets.facesRedirect(req, res, loginUrl);
        }
    }

}
