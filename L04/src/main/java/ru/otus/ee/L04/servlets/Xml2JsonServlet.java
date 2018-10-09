package ru.otus.ee.L04.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/xml2json", name = "Xml2JsonServlet")
public class Xml2JsonServlet extends HttpServlet {

    private static final String FILE = "employes_jaxb.xml";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("start servlet.<br>");

        try {

//        } catch () {
//            e.printStackTrace();
        } finally {
            out.println("final servlet.<br>");
            out.close();
        }
    }
}
