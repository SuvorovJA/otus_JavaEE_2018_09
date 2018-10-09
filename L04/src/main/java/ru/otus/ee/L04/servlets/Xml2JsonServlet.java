package ru.otus.ee.L04.servlets;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

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

@WebServlet(value = "/xml2json", name = "Xml2JsonServlet")
public class Xml2JsonServlet extends HttpServlet {

    private static final String FILEx = "employes_jaxb.xml";
    private static final String FILEj = "employes.json";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("start servlet.<br>");
        File folder = (File) getServletContext().getAttribute(ServletContext.TEMPDIR);
        File filex = new File(folder, FILEx);
        File filej = new File(folder, FILEj);
        out.println("Import from file: " + filex.toString() + "<br>");
        try {
            JSONObject jsonObject = XML.toJSONObject(new String(Files.readAllBytes(filex.toPath())));
            String json = jsonObject.toString(2);
            out.println("<pre>" + json + "</pre>");
            out.println("Export to file: " + filej.toString() + "<br>");
            Files.write(filej.toPath(), json.getBytes());
            out.println("Json file length = " + filej.length() + "<br>");
        } catch (Exception e) {
            out.println("Exception occured. " + e.getCause().getMessage());
            e.printStackTrace();
        } finally {
            out.println("final servlet.<br>");
            out.close();
        }
    }
}
