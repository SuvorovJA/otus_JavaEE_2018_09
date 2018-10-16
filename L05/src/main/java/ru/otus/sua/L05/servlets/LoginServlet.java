package ru.otus.sua.L05.servlets;

import ru.otus.sua.L05.validation.User;

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
import java.io.Writer;
import java.util.Set;

@WebServlet(name = "LoginServlet",urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    public static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

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
            request.getSession().setAttribute("user",user);
        } else {
            out.println(violationSet.toString());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
