CREATE TABLE store (
    id SERIAL PRIMARY KEY,
    display_name CHAR(64) NOT NULL,
    address TEXT NOT NULL
);

INSERT INTO store (display_name, address) VALUES ('Market Basket', '400 Somerville Ave, Somerville, MA');
