<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="doit" uri="/stattags" %>
<doit:statistic/>

<jsp:include page="execute"/>

<p style="color: red;">${errorString}</p>

<div>
    <form action="${pageContext.request.contextPath}/execute" method="post">
        <div class="scriptform">
            <label for="jsscript">Serverside JS Script</label>
            <textarea rows="10" cols="60" id="jsscript" name="jsscript">
                print("hello from js to tomcat console");
            </textarea>
        </div>
        <button type="submit">Run</button>
    </form>
</div>