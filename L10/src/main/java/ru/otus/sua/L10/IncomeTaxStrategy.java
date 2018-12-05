package ru.otus.sua.L10;

public interface IncomeTaxStrategy {

    //    Нп = (До - Ро) * Нс /100, где
//До – доходы (от реализации и внереализационные) нарастающим итогом с начала года;
//Ро – расходы (связанные с производством и реализацией и внереализационные);
//Нс - налоговая ставка отчетного периода (год);
//Нп – размер налога на прибыль за отчетный период;
    default double calculator(double taxRate, double revenues, double expenses) {
        return (revenues - expenses) * taxRate / 100;
    }
}
