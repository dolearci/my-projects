package cz.muni.fi.pv168.cashflow.business.model;

import java.time.LocalDate;
import java.util.Objects;

public class Transaction extends Entity {

    private String receiver;
    private TransactionType transactionType;
    private double amount;
    private Currency currency;
    private Category category;
    private LocalDate date;
    private String variableSymbol;
    private String constantSymbol;
    private String specificSymbol;
    private String message;

    public Transaction(
            String guid,
            String receiver,
            TransactionType transactionType,
            double amount,
            Currency currency,
            Category category,
            LocalDate date,
            String variableSymbol,
            String constantSymbol,
            String specificSymbol,
            String message) {
        super(guid);
        setReceiver(receiver);
        setTransactionType(transactionType);
        this.amount = amount;
        setCurrency(currency);
        setCategory(category);
        setDate(date);
        this.variableSymbol = variableSymbol;
        this.constantSymbol = constantSymbol;
        this.specificSymbol = specificSymbol;
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = Objects.requireNonNull(receiver, "receiver must not be null");
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = Objects.requireNonNull(transactionType, "amount must not be null");
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = Objects.requireNonNull(currency, "currency must not be null");
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = Objects.requireNonNull(category, "category must not be null");
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = Objects.requireNonNull(date, "date must not be null");
    }

    public String getVariableSymbol() {
        return variableSymbol;
    }

    public void setVariableSymbol(String variableSymbol) {
        this.variableSymbol = variableSymbol;
    }

    public String getConstantSymbol() {
        return constantSymbol;
    }

    public void setConstantSymbol(String constantSymbol) {
        this.constantSymbol = constantSymbol;
    }

    public String getSpecificSymbol() {
        return specificSymbol;
    }

    public void setSpecificSymbol(String specificSymbol) {
        this.specificSymbol = specificSymbol;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
