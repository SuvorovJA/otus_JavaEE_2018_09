package ru.otus.sua.L07.infosystem;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InfoItemSessions extends InfoItem {
    private String json;

    public InfoItemSessions(String json) {
        super(System.currentTimeMillis());
        this.json = json;
    }
}