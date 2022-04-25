CREATE TABLE users
(
    id INTEGER PRIMARY KEY,
    name CHARACTER VARYING(50) NOT NULL,
    surname CHARACTER VARYING(50) NOT NULL,
    role CHARACTER VARYING(20) NOT NULL,
    CHECK ((Name != '') AND (Surname != '') AND (Role != ''))
);

CREATE TABLE transactions
(
    id SERIAL PRIMARY KEY,
    userid INTEGER REFERENCES Users (id),
    type CHARACTER VARYING (50) NOT NULL,
    amount DECIMAL (15,2) NOT NULL,
    time TIMESTAMPTZ NOT NULL
);

CREATE TABLE cards
(
    id_card INTEGER PRIMARY KEY UNIQUE,
    account DECIMAL (16),
    balance Decimal(15,2) NOT NULL,
    currency varchar(30) NOT NULL,
    expiration_date date,
    pincode VARCHAR (32),
    id_user INTEGER NOT NULL,
    status text NOT NULL,
    FOREIGN KEY (id_user) references users (id)
);