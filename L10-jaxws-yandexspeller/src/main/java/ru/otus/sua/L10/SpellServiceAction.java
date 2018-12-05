package ru.otus.sua.L10;

import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L10.jaxws.CheckTextRequest;
import ru.otus.sua.L10.jaxws.CheckTextResponse;
import ru.otus.sua.L10.jaxws.SpellError;
import ru.otus.sua.L10.jaxws.SpellService;

import javax.ejb.Stateless;
import javax.xml.ws.WebServiceRef;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Stateless
public class SpellServiceAction {

    @WebServiceRef(wsdlLocation = "https://speller.yandex.net/services/spellservice?WSDL")
    private SpellService service;

    public String action(String stringRequest) {
        log.info("REQUEST: {}", stringRequest);
        CheckTextRequest textRequest = new CheckTextRequest();
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
        return stringResult;
    }
}
