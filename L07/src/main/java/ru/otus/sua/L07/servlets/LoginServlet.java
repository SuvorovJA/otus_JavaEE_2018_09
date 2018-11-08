package ru.otus.sua.L07.servlets;

import ru.otus.sua.L07.entities.validation.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    @SuppressWarnings("Duplicates")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        PrintWriter out = response.getWriter();
        out.println("login:" + login);
        out.println("password:" + password);
        User user = new User(login, password);
        Set<ConstraintViolation<User>> violationSet = validator.validate(user);
        if (violationSet.isEmpty()) {
            out.println("VALID");
            request.getSession().setAttribute("user", user);
        } else {
            // http://download.oracle.com/otn-pub/jcp/bean_validation-1.0-fr-oth-JSpec/bean_validation-1_0-final-spec.pdf
            // message interpolation
            // https://www.programcreek.com/java-api-examples/javax.validation.ConstraintViolation
            // tons of examples
            out.println("-----");
            ConstraintViolation<User> violation = violationSet.iterator().hasNext() ? violationSet.iterator().next() : null;
            String message = (violation != null) ? (violation.getPropertyPath() + ": " + violation.getMessage()) : "0_o?";
            out.println(message);
        }
    }

}
