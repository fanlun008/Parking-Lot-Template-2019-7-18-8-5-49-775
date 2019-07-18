CREATE TABLE parkinglot (
    id VARCHAR PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE,
    capacity INT,
    position VARCHAR
);