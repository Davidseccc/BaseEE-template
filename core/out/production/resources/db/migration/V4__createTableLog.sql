
create table log
(
	id           BIGSERIAL PRIMARY KEY,
	eventType    VARCHAR(64),
	user_id      INTEGER REFERENCES chemuser NOT NULL,
	logSection   VARCHAR (64),
	description  TEXT,
	timestamp    TIMESTAMP,
	uuid         VARCHAR(255)
);

