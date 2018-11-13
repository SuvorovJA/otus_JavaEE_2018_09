package ru.otus.sua.L07.statisticSubsystem;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class StatTagHandler extends BodyTagSupport {


    @Override
    public int doStartTag() throws JspException {
        return super.doStartTag();
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
//
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }

    @Override
    public int doAfterBody() throws JspException {
        return super.doAfterBody();
    }
}
