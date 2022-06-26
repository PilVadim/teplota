CREATE TABLE places (
  id INT GENERATED ALWAYS AS IDENTITY,
  name VARCHAR(30),
  latitude NUMERIC (7,4) NOT NULL,
  longitude NUMERIC (8,4) NOT NULL,
  period INT NOT NULL,
  enabled BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY(id)
);

CREATE TABLE temperatures (
  id INT GENERATED ALWAYS AS IDENTITY,
  celsius NUMERIC (4,2) NOT NULL,
  placeid INT NOT NULL,
  moment TIMESTAMP,
  PRIMARY KEY(id),  
  CONSTRAINT fk_place
	FOREIGN KEY(placeid) 
	   REFERENCES places(id)
);

CREATE INDEX moment_index ON temperatures(moment);

INSERT INTO places( name, latitude, longitude, period, enabled) VALUES ( 'Saint Petersburg', 59.8944,30.2642, 300,TRUE );

