DROP TABLE IF EXISTS Cakes;

CREATE TABLE Cakes (
  id uuid default random_uuid() PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  description VARCHAR(250) NOT NULL,
  imageurl VARCHAR(250) NOT NULL
);