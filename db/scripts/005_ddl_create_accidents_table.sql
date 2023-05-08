CREATE TABLE accidents (
  id SERIAL PRIMARY KEY,
  name VARCHAR(128) NOT NULL,
  text VARCHAR(128) NOT NULL,
  address VARCHAR(128) NOT NULL,
  accident_type_id INT REFERENCES accident_types(id),
  accident_rule_id INT REFERENCES accident_rules(id)
);