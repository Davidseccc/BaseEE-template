CREATE TABLE IF NOT EXISTS doi
(
  id            SERIAL PRIMARY KEY,
  doi           VARCHAR(1024),
  compound_id   INTEGER references compound NOT NULL
);