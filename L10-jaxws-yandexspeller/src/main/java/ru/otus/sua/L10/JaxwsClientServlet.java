package ru.otus.sua.L10;

import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L10.jaxws.CheckTextRequest;
import ru.otus.sua.L10.jaxws.CheckTextResponse;
import ru.otus.sua.L10.jaxws.SpellError;
import ru.otus.sua.L10.jaxws.SpellService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import javax.xml.ws.soap.SOAPFaultException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@WebServlet(name = "JaxwsClientServlet", urlPatterns = "/spellCheck")
public class JaxwsClientServlet extends HttpServlet {

    @WebServiceRef(wsdlLocation = "https://speller.yandex.net/services/spellservice?WSDL")
    private SpellService service;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
            CheckTextRequest textRequest = new CheckTextRequest();
            String stringRequest = validate(request);
            log.info("REQUEST: {}", stringRequest);
            textRequest.setText(stringRequest);
            textRequest.setLang("ru");
            CheckTextResponse result = service.getSpellServiceSoap().checkText(textRequest);
            String stringResult = result.getSpellResult().getError()
                    .stream()
                    .map(SpellError::getS)
                    .flatMap(List::stream)
                    .collect(Collectors.joining(";"));
            stringResult += " (Codes: " + result.getSpellResult().getError()
                    .stream()
                    .map(SpellError::getCode)
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")) + ")";
            log.info("RESPONSE: {}", stringResult);
            request.setAttribute("correction", stringResult);
        } catch (IllegalArgumentException | SOAPFaultException e) {
            request.setAttribute("correction", "<strong>ERR: " + e.getMessage() + "</strong>");
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    private String validate(HttpServletRequest request) throws IllegalArgumentException {
        String result = request.getParameter("word");
        if (result == null || result.isEmpty()) throw new IllegalArgumentException("(no word)");
        return result;
    }

}
