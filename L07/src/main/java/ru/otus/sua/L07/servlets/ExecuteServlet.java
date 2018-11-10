package ru.otus.sua.L07.servlets;

import ru.otus.sua.L07.entities.validation.SiteUser;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ExecuteServlet", urlPatterns = "/execute")
public class ExecuteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SiteUser siteUser = (SiteUser) request.getSession().getAttribute("AuthenticatedUser");
        if (siteUser != null) {
            request.removeAttribute("errorString");
        } else {
            request.setAttribute("errorString", "Не произведен вход, исполнение не разрешено ");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SiteUser siteUser = (SiteUser) request.getSession().getAttribute("AuthenticatedUser");
        if (siteUser != null) {
            ScriptEngineManager engineManager = new ScriptEngineManager();
            ScriptEngine engine = engineManager.getEngineByName("nashorn");
            try {
                engine.eval(request.getParameter("jsscript"));
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/scripts.jsp");

    }
}

