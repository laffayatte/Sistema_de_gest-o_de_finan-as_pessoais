-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema db_financas
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db_financas
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_financas` DEFAULT CHARACTER SET utf8 ;
USE `db_financas` ;

-- -----------------------------------------------------
-- Table `db_financas`.`financas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_financas`.`financas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `financas` FLOAT NOT NULL,
  `data` VARCHAR(45) NOT NULL,
  `categorias` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
