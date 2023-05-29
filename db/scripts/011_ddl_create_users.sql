CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    enabled boolean DEFAULT true,
    authority_id int NOT NULL REFERENCES authorities(id)
);