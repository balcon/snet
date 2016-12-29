CREATE SCHEMA snet;

CREATE TABLE snet.users (
  userId BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  firstName varchar(255) NOT NULL,
  lastName varchar(255) NOT NULL,
  username varchar(255) NOT NULL,
  passHash varchar(255) NOT NULL,
);
