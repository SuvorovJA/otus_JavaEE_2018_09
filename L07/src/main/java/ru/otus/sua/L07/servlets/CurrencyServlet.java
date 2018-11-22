package ru.otus.sua.L07.servlets;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@ApplicationScoped
@WebServlet(name = "CurrencyServlet", urlPatterns = "/currency", loadOnStartup = 1)
@Slf4j
public class CurrencyServlet extends HttpServlet {

    private Transformer transformer;

    @Override
    public void init() {
//        InputStream inputStream = this.getServletContext().getResourceAsStream("WEB-INF/classes/currency_xml2json.xsl");
        InputStream inputStream = this.getClass().getResourceAsStream("/currency_xml2json.xsl");
        StreamSource streamSource = new StreamSource(inputStream);
        try {
            transformer = TransformerFactory.newInstance().newTransformer(streamSource);
        } catch (TransformerConfigurationException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = getCurrency();
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        log.info(json);
        response.getWriter().write(json);
    }


    public String getCurrency() throws IOException {
        if (transformer == null) init();
        if (transformer == null) {
            return "";
        } else {
            HttpURLConnection connection = (HttpURLConnection) (new URL("https://www.cbr-xml-daily.ru/daily_utf8.xml")).openConnection();
            StreamSource xmlsource = new StreamSource(connection.getInputStream());
            StringWriter sw = new StringWriter();
            try {
                transformer.transform(xmlsource, new StreamResult(sw));
            } catch (TransformerException e) {
                log.error(e.getMessage());
                return "";
            }
            return sw.toString();
        }
    }
}
