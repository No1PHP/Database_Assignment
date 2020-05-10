DROP DATABASE `restaurant`;
CREATE DATABASE `restaurant`;
USE `restaurant`;

DROP TABLE IF EXISTS Recipe;
CREATE TABLE Recipe (
    `recipeID`              INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `recipeName`            CHAR(30) NOT NULL UNIQUE ,
    `price`                 FLOAT NOT NULL,

    PRIMARY KEY (`recipeID`)
);

DROP TABLE IF EXISTS AccessInfo;
CREATE TABLE AccessInfo(
    `position`              VARCHAR(20) PRIMARY KEY,
    `AccessToMaterial`      BOOLEAN NOT NULL,
    `AccessToStaff`         BOOLEAN NOT NULL,
    `AccessToStall`         BOOLEAN NOT NULL
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

DROP TABLE IF EXISTS Stall;
CREATE TABLE Stall(
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

DROP TABLE IF EXISTS TransactionRecord;
CREATE TABLE TransactionRecord (
    `TransactionID`         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `stall_id`              INT UNSIGNED NOT NULL,
    `recipeID`              INT UNSIGNED,
    `TransactionTime`       DATETIME NOT NULL,
    `numbers`               TINYINT DEFAULT 1,
    `TransactionPrice`      FLOAT NOT NULL,

    PRIMARY KEY (`TransactionID`),
    FOREIGN KEY (`recipeID`) REFERENCES Recipe (`recipeID`)  ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (`stall_id`) REFERENCES Stall (`stall_id`) ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE IF EXISTS Account;
CREATE TABLE Account(
    `staffId`               INT UNSIGNED PRIMARY KEY,
    `position`              VARCHAR(20) NOT NULL,
    `accountName`           VARCHAR(20) NOT NULL UNIQUE ,
    `password`              VARCHAR(20) NOT NULL DEFAULT '123456',

    FOREIGN KEY (`staffId`) REFERENCES Staff(`staff_id`) ON UPDATE CASCADE ON DELETE CASCADE ,
    FOREIGN KEY (`position`) REFERENCES AccessInfo(`position`) ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE IF EXISTS `OperationRecord`;
CREATE TABLE `OperationRecord`(
    `operationId`           INT UNSIGNED PRIMARY KEY,
    `staffId`               INT UNSIGNED NOT NULL,
    `operationType`         TINYINT NOT NULL,
    `note`                  TEXT,
    `operationTime`         DATETIME NOT NULL,
    `willSendUpdateMessage` TINYINT(1),-- check bit Of 推送通知

    FOREIGN KEY(`staffId`) REFERENCES Staff(`staff_id`) ON UPDATE CASCADE ON DELETE RESTRICT
);

DROP TABLE IF EXISTS MaterialOrder;
CREATE TABLE MaterialOrder (
    `op_OrderID`            INT UNSIGNED NOT NULL,
    `op_storageID`          INT UNSIGNED DEFAULT NULL,
    `materialID`            INT UNSIGNED NOT NULL,
    `amount`                FLOAT NOT NULL,

    PRIMARY KEY (`op_OrderID`),
    FOREIGN KEY(`materialID`) REFERENCES Material(`id`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(`op_OrderID`) REFERENCES OperationRecord(`operationId`) ON UPDATE CASCADE ON DELETE CASCADE ,
    FOREIGN KEY(`op_StorageID`) REFERENCES OperationRecord(`operationId`) ON UPDATE CASCADE ON DELETE RESTRICT
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
    `recipe_id`             INT UNSIGNED NOT NULL,
    `material_id`           INT UNSIGNED NOT NULL,

    PRIMARY KEY (`recipe_id`, `material_id`),
    FOREIGN KEY (`recipe_id`) REFERENCES Recipe(`recipeID`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (`material_id`) REFERENCES Material(`id`) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `Recipe_Stall_Association`;
CREATE TABLE `Recipe_Stall_Association`(
    `recipe_id`             INT UNSIGNED NOT NULL,
    `stall_id`              INT UNSIGNED NOT NULL,

    PRIMARY KEY (`recipe_id`, `stall_id`),
    FOREIGN KEY (`recipe_id`) REFERENCES Recipe(`recipeID`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (`stall_id`) REFERENCES Stall(`stall_id`) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS MaterialUsage;
CREATE TABLE MaterialUsage(
    `usage_id`              INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `stallID`               INT UNSIGNED NOT NULL,
    `materialID`            INT UNSIGNED NOT NULL,
    `time`                  DATETIME NOT NULL,
    `amount`                FLOAT NOT NULL,

    PRIMARY KEY (`usage_id`),
    FOREIGN KEY (`stallID`) REFERENCES Stall(`stall_id`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (`materialID`) REFERENCES Material(`id`) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;