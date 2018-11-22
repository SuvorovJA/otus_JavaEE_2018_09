package ru.otus.sua.L07.infosystem;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class InfoItemCurrency extends InfoItem {
    private String json;

    public InfoItemCurrency(String json) {
        super(System.currentTimeMillis());
        this.json = json;
    }
}
