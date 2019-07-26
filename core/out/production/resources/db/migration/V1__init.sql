CREATE TABLE IF NOT EXISTS researcher
(
  id               SERIAL PRIMARY KEY,
  email            VARCHAR(255),
  password         VARCHAR(1024),
  token            VARCHAR(1024),
  deletedat        TIMESTAMP,
  superuser        BOOLEAN,
  admin            BOOLEAN,
  superadmin       BOOLEAN,
  sendnotification BOOLEAN
);


CREATE TABLE IF NOT EXISTS invitro
(
  id               SERIAL PRIMARY KEY,
  value            DOUBLE PRECISION,
  value_text       VARCHAR(1024),
  conditions       VARCHAR(255),
  citation         DOUBLE PRECISION,
  doi              VARCHAR(1024),
  note             VARCHAR(1024)
);

CREATE TABLE IF NOT EXISTS compound
(
  id               SERIAL PRIMARY KEY,
  k                INTEGER,
  smiles           VARCHAR(1024),
  ion              VARCHAR(255),
  mw               DOUBLE PRECISION,
  notes            VARCHAR(1024),
  invitro_id       INTEGER references invitro NOT NULL
);
CREATE INDEX ON compound (invitro_id);


CREATE TABLE IF NOT EXISTS synonymum
(
  id            SERIAL PRIMARY KEY,
  name          VARCHAR(255),
  note          VARCHAR(1024),
  compound_id   INTEGER references compound NOT NULL
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
  note          VARCHAR(1024),
  invitro_id    INTEGER  references invitro NOT NULL
);
CREATE INDEX ON quantity (invitro_id);


CREATE TABLE IF NOT EXISTS target
(
  id            BIGSERIAL PRIMARY KEY,
  name          VARCHAR(1024),
  abbreviation  VARCHAR(1024),
  note          VARCHAR(1024),
  invitro_id    INTEGER  references invitro NOT NULL
);
CREATE INDEX ON target (invitro_id);