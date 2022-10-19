-- Insert data into "Currency" table
INSERT INTO "Currency" ("guid", "code", "name", "rate")
VALUES ('c1_guid', 'CZK', 'Czech koruna', 1),
       ('c2_guid', 'USD', 'US Dollar', 23),
       ('c3_guid', 'EUR', 'Euro', 25),
       ('c4_guid', 'GBP', 'British Pound', 28);

-- Insert data into "Category" table
INSERT INTO "Category" ("guid", "name")
VALUES ('cat1_guid', 'Not selected'),
       ('cat2_guid', 'Groceries'),
       ('cat3_guid', 'Utilities'),
       ('cat4_guid', 'Entertainment');

-- Insert data into "Transaction" table
INSERT INTO "Transaction" ("guid", "receiver", "amount", "date", "type", "currencyId", "categoryId", "variableSymbol",
                           "constantSymbol", "specificSymbol", "message")
VALUES ('t1_guid', 'John Doe', 100, '2023-01-01', 'INCOME', 1, 1, '12345', '6789', 'ABCD', 'ahojky'),
       ('t2_guid', 'Jane Doe', 50, '2023-01-02', 'OUTCOME', 2, 2, '54321', '9876', 'WXYZ', 'ahojky');

-- Insert data into "Template" table
INSERT INTO "Template" ("guid", "templateName", "receiver", "amount", "type", "currencyId", "categoryId",
                        "variableSymbol", "constantSymbol", "specificSymbol", "message")
VALUES ('temp1_guid', 'Temaplate X', 'Company X', 200, 'INCOME', 1, 1, '98765', '4321', 'WXYZ', 'ahojky'),
       ('temp2_guid', 'Temaplate X', 'Company Y', 30, 'OUTCOME', 2, 2, '56789', '1234', 'DCBA', 'ahojky');
