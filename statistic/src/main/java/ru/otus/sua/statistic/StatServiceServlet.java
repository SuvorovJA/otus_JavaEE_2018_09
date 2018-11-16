package ru.otus.sua.statistic;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StatServiceServlet", urlPatterns = "/statistic")
public class StatServiceServlet extends HttpServlet {

    @Override
    public void init() {

        // escalate stats params to App level

        getServletContext().setAttribute("ru.otus.sua.statistic.MARKER_NAME",
                getServletConfig().getInitParameter("ru.otus.sua.statistic.MARKER_NAME"));

        // values CUSTOMTAG,  FILTER
        getServletContext().setAttribute("ru.otus.sua.statistic.INTEGRATION_TYPE",
                getServletConfig().getInitParameter("ru.otus.sua.statistic.INTEGRATION_TYPE"));

        // values ENABLED, DISABLED
        getServletContext().setAttribute("ru.otus.sua.statistic.GATHERING",
                getServletConfig().getInitParameter("ru.otus.sua.statistic.GATHERING"));

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (getServletContext().getAttribute("ru.otus.sua.statistic.GATHERING").equals("ENABLED")) {

            StatisticEntity statisticEntity = (new StatisticEntityFactory(request)).getEntity();

            Long id = StatisticEntityDAO.saveStatisticEntityWithIdReturn(statisticEntity);

            request.getSession().setAttribute("previousStatisticId", id);

            JsonObject jsonObject = Json.createObjectBuilder().add("statisticId", id).build();

            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonObject);

        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (getServletContext().getAttribute("ru.otus.sua.statistic.GATHERING").equals("ENABLED")) {

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/statisticView.jsp");
            dispatcher.forward(request, response);

        }
    }
}
