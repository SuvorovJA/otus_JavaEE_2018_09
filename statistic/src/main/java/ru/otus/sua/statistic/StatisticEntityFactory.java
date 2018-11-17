package ru.otus.sua.statistic;

import lombok.Getter;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

@Getter
public class StatisticEntityFactory {

    private final StatisticEntity entity;
    private final String UNDEF = "*undef*";

    public StatisticEntityFactory(HttpServletRequest request) {

        entity = new StatisticEntity();

        entity.setCookies(Arrays.asList(request.getCookies()));
        entity.setSession(this.getSession(request));

        final UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();
        final ReadableUserAgent userAgent = parser.parse(request.getHeader("User-Agent"));
        entity.setUserAgent(userAgent.toString());
        entity.setBrowser(userAgent.getName() + " " + userAgent.getVersionNumber().getMajor());

        final String clientDateTimeValue = request.getParameter("clientDateTimeValue");
        final String clientTimeZoneValue = request.getParameter("clientTimeZoneValue");
        entity.setClientTZOffset(parze2long(clientTimeZoneValue));
        entity.setClientTimestamp(parze2long(clientDateTimeValue));

        entity.setIpaddress(this.getAddress(request));
        entity.setLogin(this.getLogin(request));
        entity.setMarker(getMarker(request));
        entity.setPage(this.getUrl(request));
        entity.setServerTimestamp(Instant.now().toEpochMilli());
        entity.setPrevId(this.getPrevId(request));
    }

    private String getMarker(HttpServletRequest request) {
        return Objects.toString(request.getServletContext().getAttribute("ru_otus_sua_statistic_MARKER_NAME"),
                "DEFAULT");
    }

    private String getLogin(HttpServletRequest request) {
        String remoteUser = request.getRemoteUser();
        String siteUser = (String) request.getSession().getAttribute("AuthenticatedUser");
        return Objects.toString(remoteUser, Objects.toString(siteUser, UNDEF));
    }

    private long parze2long(String input) {
        try {
            if (input != null && !input.isEmpty()) return Long.parseLong(input);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private long getPrevId(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        Long preId = (Long) httpSession.getAttribute("previousStatisticId");
        if (preId == null) {
            preId = -1L;
        }
        return preId;
    }

    private String getAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        return Objects.toString(ipAddress, Objects.toString(request.getRemoteAddr(), UNDEF));
    }

    private String getSession(HttpServletRequest request) {
        return Objects.toString(request.getSession(false).getId(), UNDEF);
    }

    private String getUrl(HttpServletRequest request) {
        String referer = request.getHeader("referer");
        Object forward = request.getAttribute("javax.servlet.forward.request_uri");
        Object myservlet = request.getRequestURL();
        return Objects.toString(referer, Objects.toString(forward, Objects.toString(myservlet, UNDEF)));
    }
}
