package ru.otus.sua.L07.infosystem;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Slf4j
@ApplicationScoped
public class WSInfoServlet implements Channel{

    @Inject
    private Differencer differencer;

    @PostConstruct
    private void init(){
        differencer.subscribe(this);
    }

    @Override
    public void update(InfoItem ii) {
        // provoke WS sessions send updates
        log.info(ii.toString());
    }

    public void start(){
        log.info("STARTED");
    }
}
