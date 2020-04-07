CREATE TABLE shopping_report (
    id BIGSERIAL PRIMARY KEY,
    store_id INT NOT NULL REFERENCES store(id),
    timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    perspective CHAR(8) NOT NULL
);

CREATE TABLE observation (
    shopping_report_id BIGINT NOT NULL REFERENCES shopping_report(id),
    grocery_item CHAR(32) NOT NULL,
    availability CHAR(16) NOT NULL,

    PRIMARY KEY(shopping_report_id, grocery_item)
);
