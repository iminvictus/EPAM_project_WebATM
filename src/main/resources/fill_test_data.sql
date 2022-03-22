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
