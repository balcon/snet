CREATE SCHEMA snet;

CREATE TABLE snet.users (
  userId BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  passHash VARCHAR(255) NOT NULL,
  firstName VARCHAR(255) NOT NULL,
  lastName VARCHAR(255) NOT NULL,
  birthday DATE NOT NULL,
  gender VARCHAR(255) NOT NULL,
  country VARCHAR(255) NOT NULL,
  imageId BIGINT NULL,
  deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE snet.messages (
  messageId BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  senderId BIGINT NOT NULL,
  receiverId BIGINT NOT NULL,
  sendingTime DATETIME NOT NULL,
  messageBody TEXT,
  unread BOOL DEFAULT TRUE
);

CREATE TABLE snet.images(
  imageId BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  image BLOB
);

CREATE TABLE snet.relationship (
  country1 varchar(255) NOT NULL,
  country2 varchar(255) NOT NULL,
  relation varchar(255) NOT NULL
)
