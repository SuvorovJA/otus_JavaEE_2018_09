package ru.otus.sua.L07.servlets;

import ru.otus.sua.L07.entities.EmployeEntity;
import ru.otus.sua.L07.entities.validation.AuthHelper;
import ru.otus.sua.L07.entities.helpers.EmployeEntityDAO;
import ru.otus.sua.L07.entities.validation.SiteUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import static java.util.Optional.ofNullable;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuthHelper.isAuthenticatedWithInfo(request);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = "";
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        SiteUser siteUser = new SiteUser(login, password);

        Set<ConstraintViolation<SiteUser>> violationSet = validator.validate(siteUser);
        if (violationSet.isEmpty()) {
            EmployeEntity entity = null;
            try {
                entity = EmployeEntityDAO.findByCredentials(siteUser);
            } catch (SQLException e) {
                errorString += "такая комбинация логина и пароля не обнаружена";
            }
            if (entity != null) request.getSession().setAttribute("AuthenticatedUser", siteUser);
        } else {
            ConstraintViolation<SiteUser> violation = violationSet.iterator().hasNext() ? violationSet.iterator().next() : null;
            errorString += ofNullable(violation).map(v -> (v.getPropertyPath() + ": " + v.getMessage())).orElse("0_o?");
        }
        if (!errorString.isEmpty()) request.getSession().setAttribute("errorString", errorString);

        response.sendRedirect(request.getContextPath() + "/login.jsp");

    }
}
