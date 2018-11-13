package ru.otus.sua.L07.servlets;

import ru.otus.sua.L07.entities.validation.AuthHelper;

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
        AuthHelper.isAuthenticated(request);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (AuthHelper.isAuthenticated(request)) {
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

