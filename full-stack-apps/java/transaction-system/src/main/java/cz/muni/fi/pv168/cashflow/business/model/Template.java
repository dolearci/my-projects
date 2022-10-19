package cz.muni.fi.pv168.cashflow.business.model;

import java.util.Objects;

public class Template extends Entity {

    private String templateName;
    private String receiver;
    private TransactionType transactionType;
    private double amount;
    private Currency currency;
    private Category category;
    private String variableSymbol;
    private String constantSymbol;
    private String specificSymbol;
    private String message;

    public Template(
            String guid,
            String templateName,
            String receiver,
            TransactionType transactionType,
            double amount,
            Currency currency,
            Category category,
            String variableSymbol,
            String constantSymbol,
            String specificSymbol,
            String message) {
        super(guid);
        setTemplateName(templateName);
        setReceiver(receiver);
        this.transactionType = transactionType;
        this.amount = amount;
        setCurrency(currency);
        setCategory(category);
        this.variableSymbol = variableSymbol;
        this.constantSymbol = constantSymbol;
        this.specificSymbol = specificSymbol;
        this.message = message;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = Objects.requireNonNull(templateName, "templateName must not be null");
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
        this.transactionType = transactionType;
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

    @Override
    public String toString() {
        return templateName;
    }
}
