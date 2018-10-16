package ru.otus.sua.L05.servlets;

import ru.otus.sua.L05.validation.User;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ExecuteServlet",urlPatterns = "/execute")
public class ExecuteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("nashorn");
        PrintWriter out = response.getWriter();
        User user = (User) request.getSession().getAttribute("user");
        if(user == null || !(user.getLogin().equals("123") && user.getLogin().equals("123"))) {
            out.println("FORBIDDEN JS EXECUTE");
        }else {
            out.println("GRANTED JS EXECUTE");
            try {
                out.println(engine.eval(request.getParameter("jsscript")));
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
