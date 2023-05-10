CREATE TABLE accidents_rules (
  id SERIAL PRIMARY KEY,
  accident_id INT REFERENCES accidents(id),
  accident_rule_id INT REFERENCES accident_rules(id)
);