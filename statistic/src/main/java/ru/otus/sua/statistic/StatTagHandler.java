package ru.otus.sua.statistic;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class StatTagHandler extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        PageContext pageContext = (PageContext) getJspContext();
        ServletContext servletContext = pageContext.getServletContext();
        String contextPath = servletContext.getContextPath() + "/statistic";

        out.print("<script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js'></script>");
        out.print("<script type='text/javascript'>" +
                "var stamp = {'clientDateTimeValue': Date.now(),'clientTimeZoneValue': (new Date()).getTimezoneOffset()};" +
                "$.ajax({url:'" + contextPath + "',type:'POST',data:stamp});" +
                "</script>");
    }

}
