package ru.otus.ee.L04.servlets;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.IntStream;

@WebServlet(value = "/xpathinxml", name = "XPathSearchServlet")
public class XPathSearchServlet extends HttpServlet {

    private static final String FILE = "employes_jaxb.xml";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("start servlet.<br>");
        File folder = (File) getServletContext().getAttribute(ServletContext.TEMPDIR);
        File file = new File(folder, FILE);
        out.println("Import from file: " + file.toString() + "<br>");


        try {
            if (file.exists()) {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                documentBuilderFactory.setValidating(false);
                DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
                Document document = builder.parse(file);
                //
                XPath xPath = XPathFactory.newInstance().newXPath();
                XPathExpression xSalary = xPath.compile("/employes/employes-list/employee/salary");
                NodeList salaries = (NodeList) xSalary.evaluate(document, XPathConstants.NODESET);
                out.println("Get " + salaries.getLength() + " salary records with content: ");
                IntStream.range(0, salaries.getLength())
                        .mapToObj(i->salaries.item(i).getTextContent())
                        .forEach(out::println);
                out.println("<br>");
                //
                if (salaries.getLength() > 0) {
                    int average = (IntStream.range(0, salaries.getLength())
                            .map(i -> Integer.parseInt(salaries.item(i).getTextContent()))
                            .sum())
                            / salaries.getLength();
                    out.println("Avegage salary: " + average + "<br>");
                    XPathExpression xFilteredEmployes = xPath.compile("/employes/employes-list/employee[salary>"+average+"]");
                    NodeList filteredEmployes = (NodeList) xFilteredEmployes.evaluate(document, XPathConstants.NODESET);
                    out.println("Get " + filteredEmployes.getLength() + " employes with salary over average: <br>");
                    if(filteredEmployes.getLength()>0){
                        IntStream.range(0, filteredEmployes.getLength())
                                .mapToObj(i->filteredEmployes.item(i).getTextContent()+"<br>")
                                .forEach(out::println);
                        out.println("<br>");
                    }
                }
                //
            }else{
                out.println("No exist file, please run step 0 and step 1.<br>");
            }
        } catch (ParserConfigurationException | SAXException | XPathExpressionException e) {
            out.println("Exception occured. " + e.getMessage());
            e.printStackTrace();
        } finally {
            out.println("final servlet.<br>");
            out.close();
        }
    }
}
