DROP SCHEMA IF EXISTS `razbank`;

CREATE SCHEMA `razbank`;

use `razbank`;
DROP TABLE IF EXISTS `contact_information`;

CREATE TABLE `contact_information`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `address_type`   int(2)       DEFAULT NULL,
    `address`        varchar(128) DEFAULT NULL,
    `address_number` varchar(10)  DEFAULT NULL,
    `city`           varchar(40)  DEFAULT NULL,
    `postal_code`    varchar(20)  DEFAULT NULL,
    `country`        varchar(40)  DEFAULT NULL,
    `phone`          varchar(40)  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = latin1;


DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer`
(
    `id`                     int(11) NOT NULL AUTO_INCREMENT,
    `name`                   varchar(45)  DEFAULT NULL,
    `last_name`              varchar(45)  DEFAULT NULL,
    `email`                  varchar(45)  DEFAULT NULL,
    `create_date`            varchar(45)  DEFAULT NULL,
    `type_customer`          int(2)       DEFAULT 0,
    `place_of_birth`         varchar(255) DEFAULT NULL,
    `birth_date`             varchar(45)  DEFAULT NULL,
    `country`                varchar(255) DEFAULT NULL,
    `country_code`           varchar(10)  DEFAULT NULL,
    `contact_information_id` int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_DETAIL_idx` (`contact_information_id`),
    CONSTRAINT `FK_DETAIL` FOREIGN KEY (`contact_information_id`) REFERENCES `contact_information` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = latin1;

DROP TABLE IF EXISTS `restriction`;

CREATE TABLE `restriction`
(
    `customer_id` int(11)      NOT NULL,
    `restriction` varchar(128) NOT NULL,
    PRIMARY KEY (`customer_id`, `restriction`),
    CONSTRAINT `FK_CUSTOMER_RESTRICTION` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;


DROP TABLE IF EXISTS `account`;

CREATE TABLE `account`
(
    `id`             int(10) NOT NULL AUTO_INCREMENT,
    `account_number` int(10)      DEFAULT NULL,
    `customer_id`    int(10)      DEFAULT NULL,
    `status`         int(2)       DEFAULT NULL,
    `tin`            varchar(255) DEFAULT NULL,
    `balance`        float(10)    DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ACCOUNT_NUMBER_UNIQUE` (`account_number`),
    KEY `FK_CUSTOMER_idx` (`customer_id`),
    CONSTRAINT `FK_CUSTOMER_ACCOUNT` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = latin1;


DROP TABLE IF EXISTS `otp`;

CREATE TABLE `otp`
(
    `id`          int(11)     NOT NULL AUTO_INCREMENT,
    `customer_id` int(11)     NOT NULL,
    `phone`       varchar(20) NOT NULL,
    `otp_code`    varchar(4)  NOT NULL,
    `expiry_time` long        NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;