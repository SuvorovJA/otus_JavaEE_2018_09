package ru.otus.sua.statistic;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StatServiceServlet", urlPatterns = "/statistic")
public class StatServiceServlet extends HttpServlet {

    @Override
    public void init() {

        // escalate stats params to App level

        getServletContext().setAttribute("ru_otus_sua_statistic_MARKER_NAME",
                getServletConfig().getInitParameter("ru_otus_sua_statistic_MARKER_NAME"));

        // values CUSTOMTAG,  FILTER
        getServletContext().setAttribute("ru_otus_sua_statistic_INTEGRATION_TYPE",
                getServletConfig().getInitParameter("ru_otus_sua_statistic_INTEGRATION_TYPE"));

        // values ENABLED, DISABLED
        getServletContext().setAttribute("ru_otus_sua_statistic_GATHERING",
                getServletConfig().getInitParameter("ru_otus_sua_statistic_GATHERING"));

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (getServletContext().getAttribute("ru_otus_sua_statistic_GATHERING").equals("ENABLED")) {

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

        if (getServletContext().getAttribute("ru_otus_sua_statistic_GATHERING").equals("ENABLED")) {

            int currentPage = 1;
            int recordsPerPage = 15; // TODO parametrize this

            if (request.getParameter("page") != null)
                currentPage = Integer.parseInt(request.getParameter("page"));

            List<StatisticEntity> list = StatisticEntityDAO.getNext((currentPage - 1) * recordsPerPage, recordsPerPage);
            long totalRecords = StatisticEntityDAO.getTotalRecords();
            long totalPages = (long) Math.ceil(totalRecords * 1.0 / recordsPerPage);

            request.setAttribute("statisticList", list);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", currentPage);

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/statisticView.jsp");
            dispatcher.forward(request, response);

        }
    }

}
