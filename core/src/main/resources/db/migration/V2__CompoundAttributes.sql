CREATE TABLE IF NOT EXISTS attribute
(
  id            SERIAL PRIMARY KEY,
  key           VARCHAR(255),
  ord           INTEGER,
  value         VARCHAR(1024),
  uuid          VARCHAR(255),
  compound_id   INTEGER references selectedCompound
);
CREATE INDEX ON attribute (compound_id);

ALTER TABLE attribute
	ADD attributeType INTEGER DEFAULT NULL ;