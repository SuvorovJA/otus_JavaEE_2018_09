package ru.otus.sua.L10;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.soap.SOAPFaultException;
import java.io.IOException;

@Slf4j
@WebServlet(name = "JaxwsClientServlet", urlPatterns = "/spellCheck")
public class JaxwsClientServlet extends HttpServlet {

    @Inject
    SpellServiceAction spellServiceAction;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
            request.setAttribute("correction", spellServiceAction.action(validate(request)));
        } catch (IllegalArgumentException | SOAPFaultException e) {
            request.setAttribute("correction", "<strong>ERR: " + e.getMessage() + "</strong>");
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    private String validate(HttpServletRequest request) throws IllegalArgumentException {
        String result = request.getParameter("word");
        if (result == null || result.isEmpty()) throw new IllegalArgumentException("(no word)");
        return result;
    }

}
