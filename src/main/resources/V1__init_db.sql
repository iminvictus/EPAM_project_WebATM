CREATE TABLE users
(
    id INTEGER PRIMARY KEY,
    name CHARACTER VARYING(50) NOT NULL,
    surname CHARACTER VARYING(50) NOT NULL,
    balance Decimal(15,2) NOT NULL,
    CHECK ((Name != '') AND (Surname != ''))
);

CREATE TABLE transactions
(
    id SERIAL PRIMARY KEY,
    userid INTEGER REFERENCES Users (id),
    type CHARACTER VARYING (50) NOT NULL,
    amount DECIMAL (15,2) NOT NULL,
    time TIMESTAMPTZ NOT NULL
)
