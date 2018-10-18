package ru.otus.sua.L05.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(name = "CurrencyServlet", urlPatterns = "/currency")
public class CurrencyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            InputStream inputStream = this.getServletContext().getResourceAsStream("WEB-INF/classes/currency_xml2json.xsl");
            StreamSource streamSource = new StreamSource(inputStream);
            Transformer transformer = TransformerFactory.newInstance().newTransformer(streamSource);
            HttpURLConnection connection = (HttpURLConnection) (new URL("https://www.cbr-xml-daily.ru/daily_utf8.xml")).openConnection();
            StreamSource xmlsource = new StreamSource(connection.getInputStream());
            StringWriter sw = new StringWriter();
            transformer.transform(xmlsource, new StreamResult(sw));
            getServletContext().log(sw.toString());
            out.print(sw.toString());
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
