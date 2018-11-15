package ru.otus.sua.statistic;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "StatServiceServlet", urlPatterns = "/statistic")
public class StatServiceServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    doGet(request,response);
//        PrintWriter out = response.getWriter();
        String clientDateTimeValue = request.getParameter("clientDateTimeValue");
        String clientTimeZoneValue = request.getParameter("clientTimeZoneValue");
        long clientTimeZoneOffset = parze2long(clientTimeZoneValue);
        long clientDateTimeEpoch =  parze2long(clientDateTimeValue);
        System.out.println(clientDateTimeEpoch);
        System.out.println(clientTimeZoneOffset);
    }

    private long parze2long(String input) {
        try {
            if (input != null && !input.isEmpty()) return Long.parseLong(input);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/statisticView.jsp");
        dispatcher.forward(request, response);
    }
}
