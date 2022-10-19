CREATE TABLE IF NOT EXISTS "Currency"
(
    `id`        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`      VARCHAR   NOT NULL UNIQUE,
    `code`      VARCHAR   NOT NULL UNIQUE,
    `name`      VARCHAR   NOT NULL UNIQUE,
    `rate`      DOUBLE    NOT NULL
);

CREATE TABLE IF NOT EXISTS "Category"
(
    `id`        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`      VARCHAR   NOT NULL UNIQUE,
    `name`      VARCHAR   NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS "Transaction"
(
    `id`             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`           VARCHAR                    NOT NULL UNIQUE,
    `receiver`       VARCHAR                    NOT NULL,
    `amount`         DOUBLE                     NOT NULL,
    `date`           DATE                       NOT NULL,
    `type`           ENUM ('INCOME', 'OUTCOME') NOT NULL,
    `currencyId`     BIGINT REFERENCES "Currency" (`id`),
    `categoryId`     BIGINT REFERENCES "Category" (`id`),
    `variableSymbol` VARCHAR                    NOT NULL,
    `constantSymbol` VARCHAR                    NOT NULL,
    `specificSymbol` VARCHAR                    NOT NULL,
    `message`        VARCHAR                    NOT NULL
);

CREATE TABLE IF NOT EXISTS "Template"
(
    `id`             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`           VARCHAR                    NOT NULL UNIQUE,
    `templateName`   VARCHAR                    NOT NULL,
    `receiver`       VARCHAR                    NOT NULL,
    `amount`         INTEGER                    NOT NULL,
    `type`           ENUM ('INCOME', 'OUTCOME') NOT NULL,
    `currencyId`     BIGINT REFERENCES "Currency" (`id`),
    `categoryId`     BIGINT REFERENCES "Category" (`id`),
    `variableSymbol` VARCHAR                    NOT NULL,
    `constantSymbol` VARCHAR                    NOT NULL,
    `specificSymbol` VARCHAR                    NOT NULL,
    `message`        VARCHAR                    NOT NULL
);
