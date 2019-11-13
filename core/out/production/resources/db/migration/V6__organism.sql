CREATE TABLE IF NOT EXISTS organism
(
  id            BIGSERIAL PRIMARY KEY,
  name          VARCHAR(1024)
);

alter table invitro
	add errorType int;

alter table invitro
	add organism_id INTEGER references organism;

alter table target
	add invitro_id INTEGER references invitro;