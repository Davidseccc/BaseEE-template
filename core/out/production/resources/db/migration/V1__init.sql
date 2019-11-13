CREATE TABLE IF NOT EXISTS chemuser
(
  id               SERIAL PRIMARY KEY,
  email            VARCHAR(255),
  name             VARCHAR(255),
  password         VARCHAR(1024),
  token            VARCHAR(1024),
  deletedat        TIMESTAMP,
  admin            BOOLEAN,
  superadmin       BOOLEAN,
  contributor      BOOLEAN,
  uuid             VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS compound
(
  id               SERIAL PRIMARY KEY,
  k                INTEGER,
  smiles           VARCHAR(1024),
  ion              VARCHAR(255),
  mw               DOUBLE PRECISION,
  notes            VARCHAR(1024),
  uuid             VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS synonymum
(
  id            SERIAL PRIMARY KEY,
  name          VARCHAR(255),
  note          VARCHAR(1024),
  compound_id   INTEGER references compound
);
CREATE INDEX ON synonymum (compound_id);

CREATE TABLE IF NOT EXISTS descriptor
(
  id            SERIAL PRIMARY KEY,
  formula       VARCHAR(255),
  mw            DOUBLE PRECISION ,
  hbd           INTEGER ,
  hba           INTEGER ,
  rb            VARCHAR(255),
  tpsa          DOUBLE PRECISION,
  atoms         INTEGER,
  clogp         DOUBLE PRECISION ,
  compound_id   INTEGER references compound NOT NULL
);
CREATE INDEX ON descriptor (compound_id);

CREATE TABLE IF NOT EXISTS quantity
(
  id            BIGSERIAL PRIMARY KEY,
  name          VARCHAR(1024),
  abbreviation  VARCHAR(1024),
  unit          VARCHAR(1024),
  note          VARCHAR(1024)
);


CREATE TABLE IF NOT EXISTS target
(
  id            BIGSERIAL PRIMARY KEY,
  name          VARCHAR(1024),
  abbreviation  VARCHAR(1024),
  note          VARCHAR(1024)
);

CREATE TABLE IF NOT EXISTS invitro
(
  id               SERIAL PRIMARY KEY,
  compound_id      INTEGER REFERENCES compound NOT NULL,
  valueoperator    CHAR,
  value            DOUBLE PRECISION,
  value_text       VARCHAR(1024),
  quantity_id      INTEGER REFERENCES quantity,
  target_id        INTEGER REFERENCES target NOT NULL,
  conditions       VARCHAR(255),
  citation         VARCHAR(2048),
  doi              VARCHAR(1024),
  note             VARCHAR(1024)
);

CREATE INDEX ON invitro (compound_id);
CREATE INDEX ON invitro (quantity_id);
CREATE INDEX ON invitro (target_id);

CREATE TABLE IF NOT EXISTS attribute
(
  id            SERIAL PRIMARY KEY,
  key           VARCHAR(255),
  ord           INTEGER,
  value         VARCHAR(1024),
  compound_id   INTEGER references compound NOT NULL,
  uuid          VARCHAR(255)
);
CREATE INDEX ON attribute (compound_id);