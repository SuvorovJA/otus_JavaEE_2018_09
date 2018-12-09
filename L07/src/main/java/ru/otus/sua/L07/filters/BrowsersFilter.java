package ru.otus.sua.L07.filters;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebFilter(filterName = "BrowsersFilter", urlPatterns = "/*")
public class BrowsersFilter implements Filter {

    private static Logger log = LoggerFactory.getLogger(BrowsersFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if (isIconsResource(request) || isCheckedBrowser(request) || isAllowedBrowser(request, response)) {
            chain.doFilter(req, resp);
        } else {
            request.getRequestDispatcher("/browser.jsp").forward(req, resp);
        }
    }

    private boolean isIconsResource(HttpServletRequest request) {
        return request.getRequestURL().toString().contains("/icons/");
    }

    private boolean isCheckedBrowser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return false;
        return Arrays.asList(cookies).stream()
                .anyMatch(c -> c.getName().equals("GoodBrowser"));
    }

    private boolean isAllowedBrowser(HttpServletRequest request, HttpServletResponse response) {

        UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();
        ReadableUserAgent agent = parser.parse(request.getHeader("User-Agent"));
        String agentName = agent.getName();
        int agentVersion = 0;
        try {
            agentVersion = Integer.parseInt(agent.getVersionNumber().getMajor());
        } catch (NumberFormatException e) {
            log.info("Not found browser version, set 0.");
        }

        log.info(agentName + " " + agentVersion);

        // TODO JAVA 1 & UNKNOWN 0 is fast workaround for access to JAXWS service http://localhost:8080/L08/InformatoryService?wsdl
        boolean result =
                (agentName.equalsIgnoreCase("OPERA") && agentVersion >= 38) ||
                        (agentName.equalsIgnoreCase("FIREFOX") && agentVersion >= 45) ||
                        (agentName.equalsIgnoreCase("CHROME") && agentVersion >= 50) ||
                        (agentName.equalsIgnoreCase("YANDEX.BROWSER") && agentVersion >= 17) ||
                        (agentName.equalsIgnoreCase("JAVA") && agentVersion >= 1) ||
                        (agentName.equalsIgnoreCase("UNKNOWN") && agentVersion >= 0) ||
                        (agentName.equalsIgnoreCase("IE") && agentVersion >= 10);

        if (result) response.addCookie(new Cookie("GoodBrowser", "OK"));

        return result;
    }

}
