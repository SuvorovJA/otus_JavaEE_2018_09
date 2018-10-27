package ru.otus.sua.servlets;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "NewsServlet", urlPatterns = "/news")
public class NewsServlet extends HttpServlet {

    @SuppressWarnings("Duplicates")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


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
        JsonArray array = root.getJsonArray("news"); // need clean array started from '['

        String callbackRequestParameter = request.getParameter("callback");

        // Construct: __gwt_jsonp__.P0.onSuccess([...arr...]);
        StringBuilder callback = new StringBuilder();
        // named array
//        callback.append(callbackRequestParameter).append("(").append(root).append(");");
        // array
//        callback.append(callbackRequestParameter).append("(").append(array).append(");");
        // test named array
//        callback.append(callbackRequestParameter).append("(").append("{\"news\":[{ \"text\" : \"Jimmy\", \"href\" : \"Webber\" }]}").append(");");
        // test array
        callback.append(callbackRequestParameter).append("(").append("[{ \"text\" : \"Jimmy\", \"href\" : \"Webber\" }]").append(");");

        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(callback.toString());
    }

}
