package ru.otus.sua.L11;

public interface CalcStrategy {
    Estimates calculator(int periods, double loanAmount, double interestRate);
}
