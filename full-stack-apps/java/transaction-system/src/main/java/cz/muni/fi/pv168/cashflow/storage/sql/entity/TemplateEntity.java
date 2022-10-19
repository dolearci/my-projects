package cz.muni.fi.pv168.cashflow.storage.sql.entity;

import cz.muni.fi.pv168.cashflow.business.model.TransactionType;

import java.util.Objects;

public record TemplateEntity(
        Long id,
        String guid,
        String templateName,
        String receiver,
        TransactionType transactionType,
        Double amount,
        long currencyId,
        long categoryId,
        String variableSymbol,
        String constantSymbol,
        String specificSymbol,
        String message) {

    public TemplateEntity(
            Long id,
            String guid,
            String templateName,
            String receiver,
            TransactionType transactionType,
            Double amount,
            long currencyId,
            long categoryId,
            String variableSymbol,
            String constantSymbol,
            String specificSymbol,
            String message) {
        this.id = id;
        this.guid = Objects.requireNonNull(guid, "guid must not be null");
        this.templateName = Objects.requireNonNull(templateName, "templateName must not be null");
        this.receiver = Objects.requireNonNull(receiver, "receiver must not be null");
        this.transactionType = Objects.requireNonNull(transactionType, "transactionType must not be null");
        this.amount = Objects.requireNonNull(amount, "amount must not be null");
        this.currencyId = currencyId;
        this.categoryId = categoryId;
        this.variableSymbol = Objects.requireNonNull(variableSymbol, "variableSymbol must not be null");
        this.constantSymbol = Objects.requireNonNull(constantSymbol, "constantSymbol must not be null");
        this.specificSymbol = Objects.requireNonNull(specificSymbol, "specificSymbol must not be null");
        this.message = Objects.requireNonNull(message, "message must not be null");
    }

    public TemplateEntity(
            String guid,
            String templateName,
            String receiver,
            TransactionType transactionType,
            Double amount,
            long currencyId,
            long categoryId,
            String variableSymbol,
            String constantSymbol,
            String specificSymbol,
            String message) {
        this(null, guid, templateName, receiver, transactionType, amount, currencyId, categoryId, variableSymbol, constantSymbol, specificSymbol, message);
    }
}
