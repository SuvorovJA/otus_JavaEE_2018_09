package ru.otus.ee.L04.servlets;

import ru.otus.ee.L03.entityes.Employes;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.stream.IntStream;

@WebServlet(value = "/json2obj", name = "Json2ObjServlet")
public class Json2ObjServlet extends HttpServlet {

    private static final String FILE = "employes.json";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("start servlet.<br>");
        File folder = (File) getServletContext().getAttribute(ServletContext.TEMPDIR);
        File file = new File(folder, FILE);
        out.println("Import from file: " + file.toString() + "<br>");
        Jsonb jsonb = JsonbBuilder.create();
        try {
            Employes employes = jsonb.fromJson(Files.newBufferedReader(file.toPath()), Employes.class);
            IntStream.range(0, employes.getEmployes().size())
                    .filter(i -> i % 2 == 0)
                    .mapToObj(i -> employes.getEmployes().get(i))
                    .forEach(e -> out.println("id " + e.getId() + " is " + e.getFullname()));
        } catch (Exception e) {
            out.println("Exception occured. " + e.getCause().getMessage());
            e.printStackTrace();
        } finally {
            out.println("final servlet.<br>");
            out.close();
        }
    }
}
