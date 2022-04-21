INSERT INTO users (id, name, surname, role)
VALUES
    (1, 'Jessie', 'Pinkman', 'CLIENT'),
    (2, 'Walter', 'White', 'CLIENT'),
    (3, 'Ethan', 'Hawk', 'CLIENT'),
    (4, 'Anakin', 'Skywalker', 'CLIENT'),
    (5, 'Darth', 'Vader', 'ADMIN');

INSERT INTO transactions (userid, type, amount, time)
VALUES
    (1, 'Deposit', 200.00, CURRENT_TIMESTAMP(2)),
    (1, 'Withdraw', 200.00, CURRENT_TIMESTAMP(2)),
    (1, 'Deposit', 200.00, CURRENT_TIMESTAMP(2)),
    (1, 'Withdraw', 200.00, CURRENT_TIMESTAMP(2)),
    (2, 'Deposit', 100000.00, CURRENT_TIMESTAMP(2)),
    (2, 'Deposit', 100000.00, CURRENT_TIMESTAMP(2)),
    (2, 'Withdraw', 1000000.00, CURRENT_TIMESTAMP(2)),
    (3, 'Withdraw', 7.00, CURRENT_TIMESTAMP(2)),
    (3, 'Withdraw', 7.00, CURRENT_TIMESTAMP(2)),
    (3, 'Withdraw', 0.07, CURRENT_TIMESTAMP(2)),
    (4, 'Deposit', 15000.00, CURRENT_TIMESTAMP(2)),
    (4, 'Withdraw', 100000.00, CURRENT_TIMESTAMP(2)),
    (5, 'Deposit', 726.00, CURRENT_TIMESTAMP(2)),
    (5, 'Withdraw', 600.00, CURRENT_TIMESTAMP(2)),
    (5, 'Withdraw', 60.00, CURRENT_TIMESTAMP(2))
;

INSERT INTO cards (id_card, account, balance, currency, expiration_date, pincode, id_user) VALUES
(1, 4276232251311225, 10000, 'RUR', date '2023-01-08', '1bd69c7df3112fb9a584fbd9edfc6c90', 1), --4000
(2, 2202322513112251, 15000, 'RUR', date '2023-01-08', 'ffc58105bf6f8a91aba0fa2d99e6f106', 1), --4001
(3, 4276322513112252, 20000, 'RUR', date '2023-02-08', 'a35fe7f7fe8217b4369a0af4244d1fca', 2), --5000
(4, 2202322513112252, 25000, 'RUR', date '2023-02-08', '73013a99f327fa0bffb15800c62c3fbb', 2), --5001
(5, 4276322513112253, 30000, 'RUR', date '2023-03-08', 'a8c6dd982010fce8701ce1aef8a2d40a', 3), --6000
(6, 2202322513112253, 35000, 'RUR', date '2023-03-08', 'ea1818cbe59c23b20f1a10a8aa083a82', 3), --6001
(7, 4276322513112254, 40000, 'RUR', date '2023-04-08', '708be71b9ab6e0a84252760579ade9f1', 4), --7000
(8, 2202322513112254, 45000, 'RUR', date '2023-04-08', '5e9d17e41f784ae361ada1d0817186f6', 4), --7001
(9, 4276322513112255, 50000, 'RUR', date '2023-05-08', '67ff32d40fb51f1a2fd2c4f1b1019785', 5), --8000
(10, 2202322513112255, 55000, 'RUR', date '2023-05-08', 'bc3c4a6331a8a9950945a1aa8c95ab8a', 5); --8001