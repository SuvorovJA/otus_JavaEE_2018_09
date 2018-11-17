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
        String contextPath = servletContext.getContextPath();

        out.print("<form action='" + contextPath + "/statistic' method='post' id='theTimeForm' target='dummyframe'>" +
                "<input type='hidden' name='clientDateTimeValue' id='clientDateTimeValue'>" +
                "<input type='hidden' name='clientTimeZoneValue' id='clientTimeZoneValue'>" +
                "<input type='button' value='Submit' style='display: none;'></form >");

        // hide json response:
        out.print("<iframe width='0' height='0' border='0' name='dummyframe' id='dummyframe' style='display: none;'></iframe>");

        out.print("<script src='timeStamp.js' type='text/javascript' onload='timeStamp();'></script>");
    }

}
