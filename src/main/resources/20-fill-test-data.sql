INSERT INTO users (id_user, name, surname, phone, email, password, secret_word, role)
VALUES (1, 'Jessie', 'Pinkman', '79001111111', 'abqpimp@gmail.com', 'ABQ4REAL', 'chemistry', 'CLIENT'),
       (2, 'Walter', 'White', '79002222222', 'mrheisenberg@gmail.com', 'c10h15n', 'BLUE', 'CLIENT'),
       (3, 'Ethan', 'Hawk', '79003333333', 'nobody@yahoo.com', 'not1mposs1ble', 'cucuruise', 'CLIENT'),
       (4, 'Anakin', 'Skywalker', '79004444444', 'landlurker@hothmail.com', 'maythe4', 'padme', 'CLIENT'),
       (5, 'Darth', 'Vader', '79005556665', 'donotmailme@deathstar.com', 'darthvaderrulez', 'emperor', 'ADMIN');

INSERT INTO cards (id_card, account, balance, currency, expiration_date, pincode, id_user, status)
VALUES (1, 4276232251311225, 10000, 'RUR', date '2023-01-08', '1bd69c7df3112fb9a584fbd9edfc6c90', 1, 'OPEN'),  --4000
       (2, 2202322513112251, 15000, 'RUR', date '2023-01-08', 'ffc58105bf6f8a91aba0fa2d99e6f106', 1, 'OPEN'),  --4001
       (3, 4276322513112252, 250000, 'RUR', date '2023-02-08', 'a35fe7f7fe8217b4369a0af4244d1fca', 2, 'OPEN'), --5000
       (4, 2202322513112252, 0, 'RUR', date '2023-02-08', '73013a99f327fa0bffb15800c62c3fbb', 2, 'OPEN'),      --5001
       (5, 4276322513112253, 700, 'RUR', date '2023-03-08', 'a8c6dd982010fce8701ce1aef8a2d40a', 3, 'OPEN'),    --6000
       (6, 2202322513112253, 77, 'RUR', date '2023-03-08', 'ea1818cbe59c23b20f1a10a8aa083a82', 3, 'OPEN'),     --6001
       (7, 4276322513112254, 55000, 'RUR', date '2023-04-08', '708be71b9ab6e0a84252760579ade9f1', 4, 'OPEN'),  --7000
       (8, 2202322513112254, 15000, 'RUR', date '2023-04-08', '5e9d17e41f784ae361ada1d0817186f6', 4, 'OPEN'),  --7001
       (9, 4276322513112255, 50000, 'RUR', date '2023-05-08', '67ff32d40fb51f1a2fd2c4f1b1019785', 5, 'OPEN'),  --8000
       (10, 2202322513112255, 55000, 'RUR', date '2023-05-08', 'bc3c4a6331a8a9950945a1aa8c95ab8a', 5, 'OPEN'); --8001

INSERT INTO transactions (id_transaction, date, amount, type, initiated_by, state, id_card)
VALUES (1, CURRENT_TIMESTAMP(2), 200.00, 'Deposit', 'CLIENT', 'Done', 1),
       (2, CURRENT_TIMESTAMP(2), 200.00, 'Withdraw', 'CLIENT', 'Done', 1),
       (3, CURRENT_TIMESTAMP(2), 200.00, 'Deposit', 'CLIENT', 'Done', 2),
       (4, CURRENT_TIMESTAMP(2), 200.00, 'Withdraw', 'CLIENT', 'Done', 2),
       (5, CURRENT_TIMESTAMP(2), 100000.00, 'Deposit', 'CLIENT', 'Done', 3),
       (6, CURRENT_TIMESTAMP(2), 100000.00, 'Deposit', 'CLIENT', 'Done', 3),
       (7, CURRENT_TIMESTAMP(2), 1000000.00, 'Withdraw', 'CLIENT', 'Done', 4),
       (8, CURRENT_TIMESTAMP(2), 7.00, 'Withdraw', 'CLIENT', 'Error', 5),
       (9, CURRENT_TIMESTAMP(2), 7.00, 'Withdraw', 'CLIENT', 'Error', 6),
       (10, CURRENT_TIMESTAMP(2), 0.07, 'Withdraw', 'CLIENT', 'Done', 5),
       (11, CURRENT_TIMESTAMP(2), 15000.00, 'Deposit', 'CLIENT', 'Done', 7),
       (12, CURRENT_TIMESTAMP(2), 100000.00, 'Withdraw', 'CLIENT', 'Done', 8),
       (13, CURRENT_TIMESTAMP(2), 726.00, 'Deposit', 'ADMIN', 'Cancelled', 9),
       (14, CURRENT_TIMESTAMP(2), 600.00, 'Withdraw', 'ADMIN', 'Cancelled', 10),
       (15, CURRENT_TIMESTAMP(2), 666.00, 'Withdraw', 'ADMIN', 'Done', 9);

INSERT INTO transfers (id_transfer, date, amount, state, initiated_by, id_card_source, id_card_destination)
VALUES (1, CURRENT_TIMESTAMP(2), 10000, 'Done', 'Client', 2, 3),
       (2, CURRENT_TIMESTAMP(2), 16000, 'Error', 'Client', 2, 3),
       (3, CURRENT_TIMESTAMP(2), 500, 'Error', 'Client', 4, 3),
       (4, CURRENT_TIMESTAMP(2), 15, 'Done', 'Client', 1, 9),
       (5, CURRENT_TIMESTAMP(2), 5000, 'Done', 'Admin', 7, 10),
       (6, CURRENT_TIMESTAMP(2), 10, 'Done', 'Admin', 9, 5),
       (7, CURRENT_TIMESTAMP(2), 15000, 'Cancelled', 'Client', 2, 8);