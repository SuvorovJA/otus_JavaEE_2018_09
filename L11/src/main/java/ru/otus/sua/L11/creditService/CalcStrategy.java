package ru.otus.sua.L11.creditService;

import ru.otus.sua.L11.entity.Estimates;

public interface CalcStrategy {
    Estimates calculator(int periods, double loanAmount, double interestRate);
}
