CREATE SCHEMA `work_time_tracker`;

use work_time_tracker;

CREATE TABLE `users` (
  `user_ID` INT NOT NULL AUTO_INCREMENT,
  `Username` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(64) NOT NULL,
  `Email_address` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`user_ID`),
  UNIQUE INDEX `user_ID_UNIQUE` (`user_ID` ASC),
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC),
  UNIQUE INDEX `Email_address_UNIQUE` (`Email_address` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1;

CREATE TABLE `entries` (
  `User_ID` INT NOT NULL,
  `Project` VARCHAR(75) NOT NULL,
  `Description` VARCHAR(500) NOT NULL,
  `date` date NOT NULL,
  `time` int)
ENGINE = InnoDB
AUTO_INCREMENT = 1;