package ru.otus.sua.L10;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.NotSupportedException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.http.HTTPException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static javax.xml.ws.handler.MessageContext.QUERY_STRING;

@Slf4j
@WebServiceProvider
@ServiceMode(value = javax.xml.ws.Service.Mode.MESSAGE)
@BindingType(value = HTTPBinding.HTTP_BINDING)
public class YandexSpellerServiceAsRESTSimulator implements Provider<Source> {

    private static final String WORD_REQUEST_PARAMETER_NAME = "word";

    @Resource
    private WebServiceContext wsContext;

    @Inject
    private SpellServiceAction spellServiceAction;

    @Override
    public Source invoke(Source request) {
        if (wsContext == null) throw new RuntimeException("dependency injection failed on wsContext");
        MessageContext msgContext = wsContext.getMessageContext();
        switch ((String) msgContext.get(MessageContext.HTTP_REQUEST_METHOD)) {
            case "DELETE":
                return doDelete(msgContext);
            case "GET":
                return doGet(msgContext);
            case "POST":
                return doPost(msgContext, request);
            case "PUT":
                return doPut(msgContext, request);
            default:
                throw new HTTPException(405);
        }
    }

    private Source doPut(MessageContext msgContext, Source request) {
        throw new NotSupportedException("PUT unimplemented");
    }

    private Source doPost(MessageContext msgContext, Source request) {
        throw new NotSupportedException("POST unimplemented");
    }

    private Source doGet(MessageContext msgContext) {
        StringBuffer text = new StringBuffer("");
        StreamSource sourceErr = new StreamSource(new StringReader(stringToXML("you must use parameter: ?word=СловоДляПроверки")));
        String query_string = (String) msgContext.get(QUERY_STRING);
        if (query_string == null) return sourceErr;
        String[] queryStringParts = query_string.split("=");
        if (!WORD_REQUEST_PARAMETER_NAME.equalsIgnoreCase(queryStringParts[0])) return sourceErr;
        String word = null;
        try {
            word = URLDecoder.decode(queryStringParts[1], "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("PROBLEM ON DECODING PARAMETER: {}",e.getMessage());
        }
        ;
        text.append(stringToXML(spellServiceAction.action(word)));
        log.info("ON REST WRAPPER: {} --> {}",word,text.toString());
        return new StreamSource(new StringReader(text.toString()));
    }

    private Source doDelete(MessageContext msgContext) {
        throw new NotSupportedException("DELETE unimplemented");
    }


    private String stringToXML(String string) {
        String xmlString = "<?xml version=\"1.0\"?><string>" + string + "</string>";
        return xmlString;
    }
}
