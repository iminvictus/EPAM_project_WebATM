INSERT INTO users (id, name, surname, balance)
VALUES
    (1, 'Jessie', 'Pinkman', 3556.00),
    (2, 'Walter', 'White', 1000000.00),
    (3, 'Ethan', 'Hawk', 3500.00),
    (4, 'Anakin', 'Skywalker', 85000.00),
    (5, 'Darth', 'Vader', 666666.00);

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
(1,42762322513112251, 10000, 'RUR', TIMESTAMP '2023-01-08', 4000, 1),
(2, 2202322513112251, 15000, 'RUR', date '2023-01-08', 4001, 1),
(3, 4276322513112252, 20000, 'RUR', date '2023-02-08', 5000, 2),
(4, 2202322513112252, 25000, 'RUR', date '2023-02-08', 5001, 2),
(5, 4276322513112253, 30000, 'RUR', date '2023-03-08', 6000, 3),
(6, 2202322513112253, 35000, 'RUR', date '2023-03-08', 6001, 3),
(7, 4276322513112254, 40000, 'RUR', date '2023-04-08', 7000, 4),
(8, 2202322513112254, 45000, 'RUR', date '2023-04-08', 7001, 4),
(9, 4276322513112255, 50000, 'RUR', date '2023-05-08', 8000, 5),
(10, 2202322513112255, 55000, 'RUR', date '2023-05-08', 8001, 5);

update users set role = 'CLIENT' where id = 1;
update users set role = 'CLIENT' where id = 2;
update users set role = 'CLIENT' where id = 3;
update users set role = 'CLIENT' where id = 4;
update users set role = 'ADMIN' where id = 5;