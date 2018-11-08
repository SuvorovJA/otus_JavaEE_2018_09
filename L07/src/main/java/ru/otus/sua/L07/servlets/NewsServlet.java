package ru.otus.sua.L07.servlets;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "NewsServlet", urlPatterns = "/news")
public class NewsServlet extends HttpServlet {

    @Override
    @SuppressWarnings("Duplicates")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Document site = Jsoup.connect("http://www.tomsk.ru/news/").get();
        Elements newsAll = site.getElementsByClass("title");
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        for (Element e : newsAll) {
            Element a = e.getElementsByTag("a").last();
            if (!(a.absUrl("href").contains("skidki/"))) {
                JsonObjectBuilder newsOne = Json.createObjectBuilder();
                newsOne.add("text", a.text());
                newsOne.add("href", a.absUrl("href"));
                jsonArray.add(newsOne.build());
            }
        }
        JsonObject root = Json.createObjectBuilder().add("news", jsonArray.build()).build();
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        getServletContext().log(root.toString());
        response.getWriter().write(root.toString());
    }

}
