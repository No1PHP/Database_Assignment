DROP DATABASE `restaurant`;
CREATE DATABASE `restaurant`;
USE `restaurant`;

DROP TABLE IF EXISTS Recipe;
CREATE TABLE Recipe (
    `recipeName`            CHAR(30) PRIMARY KEY,
    `price`                 FLOAT NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS AccessInfo;
CREATE TABLE AccessInfo(
    `position`              VARCHAR(20) PRIMARY KEY,
    `AccessToMaterial`      BOOLEAN NOT NULL,
    `AccessToStaff`         BOOLEAN NOT NULL,
    `AccessToStall`         BOOLEAN NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS Material;
CREATE TABLE Material (
    `name`                  CHAR(30) PRIMARY KEY,
    `type`                  TINYINT NOT NULL,
    `unit_price`            FLOAT NOT NULL,
    `availableAmount`       FLOAT NOT NULL DEFAULT 0,
    `availableTime`         FLOAT NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS Stall;
CREATE TABLE Stall(
    `stall_name`            CHAR(30) PRIMARY KEY,
    `stall_location`        SMALLINT NOT NULL UNIQUE,
    `stall_rent`            FLOAT NOT NULL,
    `oper_cost_last_month`  FLOAT DEFAULT 0.0,
    `oper_time`             INT UNSIGNED NOT NULL DEFAULT 0,
    `Aver_mon_sales`        FLOAT DEFAULT 0.0,
    `Aver_sales_amount`     FLOAT DEFAULT 0.0
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 有时间
DROP TABLE IF EXISTS Staff;
CREATE TABLE Staff(
    `staff_id`              INT UNSIGNED AUTO_INCREMENT,
    `staff_name`            CHAR(30) NOT NULL,
    `staff_category`        TINYINT DEFAULT NULL,
    -- 需要补充工作类型
    `Effe_work_time_starts` TIME,
    `Effe_work_time_end`    TIME,

    PRIMARY KEY ( `staff_id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS TransactionRecord;
CREATE TABLE TransactionRecord (
    `TransactionID`         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `stall_name`            CHAR(30) NOT NULL,
    `recipeName`            CHAR(30) NOT NULL ,
    `TransactionTime`       DATETIME NOT NULL,
    `numbers`               TINYINT DEFAULT 1,
    `TransactionPrice`      FLOAT NOT NULL,

    PRIMARY KEY (`TransactionID`),
    FOREIGN KEY (`recipeName`) REFERENCES Recipe (`recipeName`)  ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (`stall_name`) REFERENCES Stall (`stall_name`) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS Account;
CREATE TABLE Account(
    `staffId`               INT UNSIGNED PRIMARY KEY,
    `position`              VARCHAR(20) NOT NULL,
    `accountName`           VARCHAR(20) NOT NULL UNIQUE ,
    `password`              VARCHAR(20) NOT NULL DEFAULT '123456',

    FOREIGN KEY (`staffId`) REFERENCES Staff(`staff_id`) ON UPDATE CASCADE ON DELETE CASCADE ,
    FOREIGN KEY (`position`) REFERENCES AccessInfo(`position`) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `OperationRecord`;
CREATE TABLE `OperationRecord`(
    `operationId`           INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `staffId`               INT UNSIGNED NOT NULL,
    `operationType`         TINYINT NOT NULL,
    `note`                  TEXT,
    `operationTime`         DATETIME NOT NULL,
    `willSendUpdateMessage` TINYINT(1),-- check bit Of 推送通知

    FOREIGN KEY(`staffId`) REFERENCES Staff(`staff_id`) ON UPDATE CASCADE ON DELETE RESTRICT
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS MaterialOrder;
CREATE TABLE MaterialOrder (
    `op_OrderID`            INT UNSIGNED NOT NULL,
    `op_storageID`          INT UNSIGNED DEFAULT NULL,
    `material_name`         CHAR(30) NOT NULL,
    `amount`                FLOAT NOT NULL,

    PRIMARY KEY (`op_OrderID`),
    FOREIGN KEY(`material_name`) REFERENCES Material(`name`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(`op_OrderID`) REFERENCES OperationRecord(`operationId`) ON UPDATE CASCADE ON DELETE CASCADE ,
    FOREIGN KEY(`op_StorageID`) REFERENCES OperationRecord(`operationId`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

-- 有时间
DROP TABLE IF EXISTS `Schedule_record`;
CREATE TABLE `Schedule_record`(
    `op_ID`                 INT UNSIGNED NOT NULL,
    `staff_id`              INT UNSIGNED NOT NULL,
    `work_time_start`       DATETIME NOT NULL,
    `work_time_end`         DATETIME NOT NULL,

    PRIMARY KEY (`op_ID`),
    FOREIGN KEY (`op_ID`) REFERENCES OperationRecord(operationId) ON UPDATE CASCADE ON DELETE CASCADE ,
    FOREIGN KEY (`staff_id`) REFERENCES Staff(`staff_id`) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `Recipe_Material_Association`;
CREATE TABLE `Recipe_Material_Association`(
    `recipe_name`           CHAR(30) NOT NULL,
    `material_name`         CHAR(30) NOT NULL,

    PRIMARY KEY (`recipe_name`, `material_name`),
    FOREIGN KEY (`recipe_name`) REFERENCES Recipe(`recipeName`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (`material_name`) REFERENCES Material(`name`) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `Recipe_Stall_Association`;
CREATE TABLE `Recipe_Stall_Association`(
    `recipe_name`           CHAR(30) NOT NULL,
    `stall_name`            CHAR(30) NOT NULL,

    PRIMARY KEY (`recipe_name`, `stall_name`),
    FOREIGN KEY (`recipe_name`) REFERENCES Recipe(`recipeName`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (`stall_name`) REFERENCES Stall(`stall_name`) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS MaterialUsage;
CREATE TABLE MaterialUsage(
    `usage_id`              INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `stall_name`            CHAR(30) NOT NULL,
    `material_name`         CHAR(30) NOT NULL,
    `storageID`             INT UNSIGNED NOT NULL,
    `time`                  DATETIME NOT NULL,
    `amount`                FLOAT NOT NULL,

    PRIMARY KEY (`usage_id`),
    FOREIGN KEY (`stall_name`) REFERENCES Stall(`stall_name`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (`storageID`) REFERENCES MaterialOrder(`op_storageID`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (`material_name`) REFERENCES Material(`name`) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;