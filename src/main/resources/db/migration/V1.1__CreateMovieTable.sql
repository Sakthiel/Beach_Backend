CREATE TABLE MOVIES(
ID INT GENERATED BY DEFAULT AS IDENTITY,
NAME VARCHAR(255),
DURATION BIGINT,
GENRE VARCHAR(255),
RATING NUMERIC(4,1),
POSTER VARCHAR(1000),
PRIMARY KEY (ID)
);
