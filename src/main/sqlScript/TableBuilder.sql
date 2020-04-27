DROP DATABASE `restaurant`;
CREATE DATABASE `restaurant`;
USE `restaurant`;

DROP TABLE IF EXISTS Recipe;
CREATE TABLE Recipe (
    `recipeID`              INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `recipeName`            CHAR(30) NOT NULL,
    `relevantIngredient`    JSON NOT NULL,
    `price`                 FLOAT NOT NULL,

    PRIMARY KEY (`recipeID`)
);

DROP TABLE IF EXISTS AccessInfo;
CREATE TABLE AccessInfo(
    `position`              VARCHAR(20) PRIMARY KEY,
    `AccessToOrder`         TINYINT(1),
    `AccessToStaff`         TINYINT(1),
    `AccessToStock`         TINYINT(1)
);

DROP TABLE IF EXISTS Material;
CREATE TABLE Material (
    `id`                    INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `name`                  CHAR(30) NOT NULL UNIQUE,
    `type`                  TINYINT NOT NULL,
    `unit_price`            FLOAT NOT NULL,
    `availableAmount`       FLOAT NOT NULL DEFAULT 0,
    `availableTime`         INT
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS StallInfo;
CREATE TABLE StallInfo(
    `stall_id`              INT UNSIGNED AUTO_INCREMENT,
    `stall_name`            CHAR(40) NOT NULL UNIQUE,
    `stall_location`        SMALLINT NOT NULL UNIQUE,
    `stall_rent`            FLOAT NOT NULL,
    `oper_cost_last_month`  FLOAT DEFAULT 0.0,
    `oper_time`             INT UNSIGNED NOT NULL DEFAULT 0,
    `Aver_mon_sales`        FLOAT DEFAULT 0.0,
    `Aver_sales_amount`     FLOAT DEFAULT 0.0,

    PRIMARY KEY ( `stall_id` )
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

-- 有时间
DROP TABLE IF EXISTS TransactionRecord;
CREATE TABLE TransactionRecord (
    `TransactionID`         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `stall_id`              INT UNSIGNED NOT NULL,
    `recipeID`              INT UNSIGNED NOT NULL,
    `TransactionTime`       DATETIME NOT NULL,
    `numbers`               TINYINT NULL DEFAULT 1,
    `TransactionPrice`      FLOAT NOT NULL,

    PRIMARY KEY (`TransactionID`),
    FOREIGN KEY (`recipeID`) REFERENCES Recipe (`recipeID`)  ON UPDATE CASCADE ON DELETE NO ACTION,
    FOREIGN KEY (`stall_id`) REFERENCES StallInfo (`stall_id`) ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE IF EXISTS Account;
CREATE TABLE Account(
    `staffId`               INT UNSIGNED PRIMARY KEY,
    `position`              VARCHAR(20),
    `accountName`           VARCHAR(20) NOT NULL,
    `password`              VARCHAR(20) NOT NULL DEFAULT '123456',

    FOREIGN KEY (`position`) REFERENCES AccessInfo(`position`) ON UPDATE CASCADE ON DELETE SET NULL
);

-- 有时间
DROP TABLE IF EXISTS `OperationRecord`;
CREATE TABLE `OperationRecord`(
    `operationId`           INT UNSIGNED PRIMARY KEY,
    `staffId`               INT UNSIGNED NOT NULL,
    `operationType`         TINYINT NOT NULL,
    `note`                  TEXT,
    `operationTime`         DATETIME NOT NULL,
    `willSendUpdateMessage` TINYINT(1),-- checkbit Of 推送通知

    FOREIGN KEY(`staffId`) REFERENCES Account(`staffId`) ON UPDATE CASCADE ON DELETE NO ACTION
);

DROP TABLE IF EXISTS MaterialOrder;
CREATE TABLE MaterialOrder (
    `order_ID`              INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `op_OrderID`            INT UNSIGNED NOT NULL,
    `op_storageID`          INT UNSIGNED DEFAULT NULL,
    `materialID`            INT UNSIGNED NOT NULL,
    `amount`                FLOAT NOT NULL,

    PRIMARY KEY (`order_ID`),
    FOREIGN KEY(`materialID`) REFERENCES Material(`id`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(`op_OrderID`) REFERENCES OperationRecord(operationId) ON UPDATE CASCADE ON DELETE RESTRICT ,
    FOREIGN KEY(`op_StorageID`) REFERENCES OperationRecord(operationId) ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

-- 有时间
DROP TABLE IF EXISTS `Schedule_record`;
CREATE TABLE `Schedule_record`(
    `Schedule_id`           INT UNSIGNED AUTO_INCREMENT,
    `op_OrderID`            INT UNSIGNED NOT NULL,
    `staff_id`              INT UNSIGNED NOT NULL,
    `work_time_start`       DATETIME NOT NULL,
    `work_time_end`         DATETIME NOT NULL,

    PRIMARY KEY (`Schedule_id`),
    FOREIGN KEY (`op_OrderID`) REFERENCES OperationRecord(operationId) ON UPDATE CASCADE ON DELETE RESTRICT ,
    FOREIGN KEY (`staff_id`) REFERENCES Staff(`staff_id`) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;