SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `jdbcrealm` ;
CREATE SCHEMA IF NOT EXISTS `jdbcrealm` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `jdbcrealm` ;

-- -----------------------------------------------------
-- Table `jdbcrealm`.`groups`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jdbcrealm`.`groups` ;

CREATE TABLE IF NOT EXISTS `jdbcrealm`.`groups` (
  `groupid` INT NOT NULL,
  `groupname` VARCHAR(255) NOT NULL,
  `username` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`groupid`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC));


-- -----------------------------------------------------
-- Table `jdbcrealm`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jdbcrealm`.`users` ;

CREATE TABLE IF NOT EXISTS `jdbcrealm`.`users` (
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `userid` INT NOT NULL,
  `groups_groupid` INT NOT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  INDEX `fk_users_groups1_idx` (`groups_groupid` ASC),
  CONSTRAINT `fk_users_groups1`
    FOREIGN KEY (`groups_groupid`)
    REFERENCES `jdbcrealm`.`groups` (`groupid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `jdbcrealm`.`whiteboard`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jdbcrealm`.`whiteboard` ;

CREATE TABLE IF NOT EXISTS `jdbcrealm`.`whiteboard` (
  `whiteboard_id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `users_username` VARCHAR(255) NOT NULL,
  `location_file` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`whiteboard_id`),
  INDEX `fk_whiteboard_users_idx` (`users_username` ASC),
  CONSTRAINT `fk_whiteboard_users`
    FOREIGN KEY (`users_username`)
    REFERENCES `jdbcrealm`.`users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
