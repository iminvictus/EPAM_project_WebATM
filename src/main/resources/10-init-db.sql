CREATE TABLE users
(
    id_user INTEGER PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    phone VARCHAR(11) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    secret_word VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    CHECK ((name != '') AND (surname != '') AND (role != '') AND (phone != '') AND (email != '') AND (password != '') AND (secret_word != ''))
);

CREATE TABLE cards
(
    id_card INTEGER PRIMARY KEY,
    account DECIMAL(16) NOT NULL,
    balance DECIMAL(15,2) NOT NULL,
    currency VARCHAR(30) NOT NULL,
    expiration_date DATE NOT NULL,
    pincode VARCHAR (32) NOT NULL,
    status VARCHAR (30) NOT NULL,
    id_user INTEGER REFERENCES users (id_user) NOT NULL,
    CHECK ((currency != '') AND (pincode != '') AND (status != ''))
);

CREATE TABLE transactions
(
    id_transaction SERIAL PRIMARY KEY,
    date TIMESTAMPTZ NOT NULL,
    amount DECIMAL (15,2) NOT NULL,
    type VARCHAR (50) NOT NULL,
    initiated_by VARCHAR (50) NOT NULL,
    state VARCHAR (50) NOT NULL,
    id_card INTEGER REFERENCES cards (id_card) NOT NULL,
    CHECK ((type != '') AND (initiated_by != '') AND (state != ''))
);

CREATE TABLE transfers
(
    id_transfer SERIAL PRIMARY KEY,
    date TIMESTAMPTZ NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    state VARCHAR (50) NOT NULL,
    initiated_by VARCHAR (50) NOT NULL,
    id_card_source INTEGER REFERENCES cards (id_card) NOT NULL,
    id_card_destination INTEGER REFERENCES cards (id_card) NOT NULL,
    CHECK ((state != '') AND (initiated_by != ''))
);