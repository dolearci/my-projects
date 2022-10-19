package cz.muni.fi.pv168.cashflow.storage.sql.entity;

import cz.muni.fi.pv168.cashflow.business.model.TransactionType;

import java.time.LocalDate;
import java.util.Objects;

public record TransactionEntity(
        Long id,
        String guid,
        String receiver,
        TransactionType transactionType,
        Double amount,
        long currencyId,
        long categoryId,
        LocalDate date,
        String variableSymbol,
        String constantSymbol,
        String specificSymbol,
        String message) {

    public TransactionEntity(
            Long id,
            String guid,
            String receiver,
            TransactionType transactionType,
            Double amount,
            long currencyId,
            long categoryId,
            LocalDate date,
            String variableSymbol,
            String constantSymbol,
            String specificSymbol,
            String message) {
        this.id = id;
        this.guid = Objects.requireNonNull(guid, "guid must not be null");
        this.receiver = Objects.requireNonNull(receiver, "receiver must not be null");
        this.transactionType = Objects.requireNonNull(transactionType, "transactionType must not be null");
        this.amount = Objects.requireNonNull(amount, "amount must not be null");
        this.currencyId = currencyId;
        this.categoryId = categoryId;
        this.date = Objects.requireNonNull(date, "date must not be null");
        this.variableSymbol = Objects.requireNonNull(variableSymbol, "variableSymbol must not be null");
        this.constantSymbol = Objects.requireNonNull(constantSymbol, "constantSymbol must not be null");
        this.specificSymbol = Objects.requireNonNull(specificSymbol, "specificSymbol must not be null");
        this.message = Objects.requireNonNull(message, "message must not be null");
    }

    public TransactionEntity(
            String guid,
            String receiver,
            TransactionType transactionType,
            Double amount,
            long currencyId,
            long categoryId,
            LocalDate date,
            String variableSymbol,
            String constantSymbol,
            String specificSymbol,
            String message) {
        this(null, guid, receiver, transactionType, amount, currencyId, categoryId, date, variableSymbol, constantSymbol, specificSymbol, message);
    }
}
