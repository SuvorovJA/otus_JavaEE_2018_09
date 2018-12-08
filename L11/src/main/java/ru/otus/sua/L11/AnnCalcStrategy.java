package ru.otus.sua.L11;

import java.util.Collections;

public interface AnnCalcStrategy extends CalcStrategy {
    /**
     * аннуитетный платеж:
     *
     * @param periods      T - количество периодов оплаты;
     * @param loanAmount   Кр - сумма кредита;
     * @param interestRate СтГ - процентная ставка, годовая начисляемая на задолженность;
     *                     <p>
     *                     Пример расчёта платежей и суммы процентов, выплаченных за период:
     *                     Т = 6 мес.; Кр = $10 000; Ст = 15% годовых/ 12 мес. = 0.0125
     *                     Пл = 10 000 * 0.0125 / (1 – 1/ (1.0125)^6) = 125 / 0.071825 = $1740
     * @return             размер платежа не зависит от i, все платежи равны между собой.
     *                     Плi = Кр*Ст / (1 - 1 / (1+Ст)^T)
     */

    default Estimates calculator(int periods, double loanAmount, double interestRate) {
        if (periods <= 0) throw new IllegalArgumentException("periods <=0");
        if (loanAmount <= 0) throw new IllegalArgumentException("loanAmount <=0");
        if (interestRate <= 0) throw new IllegalArgumentException("interestRate <=0");
        double st = interestRate / 100 / 12;
        return new Estimates(Collections.singletonList(loanAmount * st / (1 - 1 / Math.pow((1 + st), periods))));
    }
}
