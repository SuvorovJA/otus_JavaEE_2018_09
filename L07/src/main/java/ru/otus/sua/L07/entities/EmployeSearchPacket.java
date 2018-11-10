package ru.otus.sua.L07.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.otus.sua.L07.entities.exceptions.InvalidSearchException;

import javax.servlet.http.HttpServletRequest;

@Getter
@EqualsAndHashCode
public class EmployeSearchPacket {

    private String fullName = "";
    private String city = "";
    private String departament = "";
    private String appointment = "";
    private String login = "";
    private String ageMaxStr = "";
    private String ageMinStr = "";
    private int ageMax = 0;
    private int ageMin = 0;

    private static String getRequestParameterIfDefined(HttpServletRequest request, String paramName) {
        return request.getParameter(paramName) == null ? "" : request.getParameter(paramName).trim();
    }

    public void setFromRequest(HttpServletRequest request) throws InvalidSearchException {

        if (request == null) throw new InvalidSearchException("нет запроса");

        fullName += getRequestParameterIfDefined(request, "search_fullName");
        city += getRequestParameterIfDefined(request, "search_city");
        appointment += getRequestParameterIfDefined(request, "search_appointment");
        departament += getRequestParameterIfDefined(request, "search_departament");
        login += getRequestParameterIfDefined(request, "search_login");
        ageMaxStr += getRequestParameterIfDefined(request, "search_age_max");
        ageMinStr += getRequestParameterIfDefined(request, "search_age_min");

        if (!isSearchable()) throw new InvalidSearchException("пустой поисковый запрос");

        ageMin = parseAge(ageMinStr);
        ageMax = parseAge(ageMaxStr);

        if (((ageMax > 0) && (ageMax < ageMin)) || (ageMax < 0) || (ageMin < 0))
            throw new InvalidSearchException("неверные границы возраста");

        // TODO need some screening request fiels ?
    }

    private int parseAge(String age) throws InvalidSearchException {
        if (!age.isEmpty()) {
            try {
                return Integer.parseInt(age);
            } catch (NumberFormatException e) {
                throw new InvalidSearchException("границы возраста указывать целыми числами");
            }
        }
        return 0;
    }

    public boolean isSearchable() {
        return (!fullName.isEmpty()) ||
                (!city.isEmpty()) ||
                (!departament.isEmpty()) ||
                (!appointment.isEmpty()) ||
                (!ageMaxStr.isEmpty()) ||
                (!ageMinStr.isEmpty()) ||
                (!login.isEmpty());
    }

    @Override
    public String toString() {
        return "Search Strings: " +
                "fullName=\'" + fullName + "\'; " +
                "ageMaxStr=\'" + ageMaxStr + "\'; " +
                "ageMax=" + ageMax + "; " +
                "ageMinStr=\'" + ageMinStr + "\'; " +
                "ageMin=" + ageMin + "; " +
                "city=\'" + city + "\'; " +
                "departament=\'" + departament + "\'; " +
                "appointment=\'" + appointment + "\'; " +
                "login=\'" + login + "\'; ";
    }
}
