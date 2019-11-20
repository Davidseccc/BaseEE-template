alter table descriptor
	add mol_weight DOUBLE PRECISION,
	add exact_mass DOUBLE PRECISION,
	add canonical_SMILES VARCHAR(1024),
	add InChI VARCHAR(1024),
	add num_atoms INTEGER,
	add num_bonds INTEGER,
	add num_residues INTEGER,
	add num_rotors INTEGER,
	add sequence INTEGER,
	add num_rings INTEGER,
	add logP DOUBLE PRECISION,
	add PSA DOUBLE PRECISION,
	add MR DOUBLE PRECISION
