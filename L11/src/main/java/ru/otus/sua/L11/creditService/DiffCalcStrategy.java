package ru.otus.sua.L11.creditService;

import ru.otus.sua.L11.entity.Estimates;

import java.util.ArrayList;
import java.util.List;

public interface DiffCalcStrategy extends CalcStrategy {
    /**
     * дифференцированный платеж:
     *
     * @param periods      T - количество периодов оплаты;
     * @param loanAmount   Кр - сумма кредита;
     * @param interestRate СтГ - процентная ставка, годовая начисляемая на задолженность;
     *
     *                     Пример расчёта платежей и суммы процентов, выплаченных за период:
     *                     Т = 6 мес.; Кр = $10 000; Ст = 15% годовых/ 12 мес. = 0.0125
     *                     Пл1 = 10000/6 + 10000*6*0.0125/6 = $1791.7 .......
     *                     Пл6 = 10000/6 + 10000*1*0.0125/6 = $1687,5
     *
     * @return          Плi - размер платежа за i - й период (i принимает значения от 1 до T);
     *                  Плi = Кр /T + Кр*(T- i +1)*Ст /Т , где
     */

    default Estimates calculator(int periods, double loanAmount, double interestRate) {
        if (periods <= 0) throw new IllegalArgumentException("periods <=0");
        if (loanAmount <= 0) throw new IllegalArgumentException("loanAmount <=0");
        if (interestRate <= 0) throw new IllegalArgumentException("interestRate <=0");
        List<Double> res = new ArrayList<>();
        double st = interestRate / 100 / 12;
        for (int i = periods; i >= 1; i--) {
            res.add(loanAmount / periods + loanAmount * i * st / periods);
        }
        return new Estimates(res);
    }
}
