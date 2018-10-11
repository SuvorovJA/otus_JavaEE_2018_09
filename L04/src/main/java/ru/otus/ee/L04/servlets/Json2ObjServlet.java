package ru.otus.ee.L04.servlets;

import ru.otus.ee.L04.entityes.EmployeEntity;

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
import java.util.Arrays;
import java.util.stream.IntStream;

@WebServlet(value = "/json2obj", name = "Json2ObjServlet")
public class Json2ObjServlet extends HttpServlet {

    private static final String FILE = "employes.json";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("start servlet.<br>");
        File folder = (File) getServletContext().getAttribute(ServletContext.TEMPDIR);
        File file = new File(folder, FILE);
        out.println("Import from file: " + file.toString() + "<br>");
        Jsonb jsonb = JsonbBuilder.create();
        try {
            EmployeEntity[] employes = jsonb.fromJson(Files.newBufferedReader(file.toPath()), EmployeEntity[].class);
            boolean oddPresent = Arrays.stream(employes).anyMatch(e -> e.getId() % 2 != 0);
            if (!oddPresent) out.println("not have odd indexes, will print all.<br>");
            Arrays.stream(employes, 0, employes.length)
                    .filter(e -> ((e.getId() % 2 == 0) || !oddPresent))
                    .forEach(e -> out.println("id " + e.getId() + " is " + e.getFullname() + "<br>"));
        } catch (Exception e) {
            out.println("Exception occured. " + e.getMessage() + "<br>");
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        } finally {
            out.println("final servlet.<br>");
            out.close();
        }
    }
}
