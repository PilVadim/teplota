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

INSERT INTO places( name, latitude, longitude, period, enabled)
    VALUES ( 'Saint Petersburg', 59.8944,30.2642, 300,TRUE );


INSERT INTO temperatures (placeid,celsius,moment)
VALUES ( 1,12.01,'2022-06-01 00:00:00.0'),
       ( 1,15.01,'2022-07-01 00:25:00.0'),
       ( 1,15.21,'2022-07-01 00:30:00.0'),
       ( 1,12.76,'2022-07-01 00:35:00.0'),
       ( 1,15.21,'2022-06-01 00:05:00.0'),
       ( 1,18.01,'2022-06-01 00:15:00.0'),
       ( 1,20.48,'2022-06-01 00:20:00.0'),
       ( 1,17.01,'2022-06-01 00:10:00.0'),
       ( 1,18.01,'2022-07-01 00:40:00.0'),
       ( 1,19.86,'2022-07-01 00:45:00.0');
