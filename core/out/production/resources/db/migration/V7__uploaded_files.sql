CREATE TABLE IF NOT EXISTS uploaded_files
(
  id            BIGSERIAL PRIMARY KEY,
  user_id       INTEGER REFERENCES chemuser NOT NULL,
  fileName      VARCHAR(1024),
  path          VARCHAR(1024),
  fileSize      DOUBLE PRECISION,
  timestamp     TIMESTAMP,
  uuid          VARCHAR(255)
);

CREATE INDEX ON uploaded_files (fileName);
CREATE INDEX ON uploaded_files (uuid);

