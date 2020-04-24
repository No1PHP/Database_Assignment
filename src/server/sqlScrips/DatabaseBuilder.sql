USE `restaurant`;

DROP TABLE IF EXISTS `Recipes`;
CREATE TABLE `Recipes` (
    `recipeID`              INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `recipeName`            CHAR(30) NOT NULL,
    `relevantIngredient`    set('待添加') NOT NULL DEFAULT '',
    `price`                 FLOAT NULL,

    PRIMARY KEY (`recipeID`)
);

DROP TABLE IF EXISTS `TransactionRecords`;
CREATE TABLE `TransactionRecords`  (
    `TransactionID`         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `stall_id`              INT UNSIGNED NOT NULL,
    `recipeID`              INT UNSIGNED NOT NULL,
    `TransactionTime`       DATETIME NOT NULL,
    `numbers`               TINYINT NULL DEFAULT 1,
    `TransactionPrice`      FLOAT NOT NULL,

    PRIMARY KEY (`TransactionID`),
    FOREIGN KEY (`recipeID`) REFERENCES `Recipes` (`recipeID`) ,
    FOREIGN KEY (`stall_id`) REFERENCES `stall` (`stall_id`) ON DELETE SET NULL ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `material`;
CREATE TABLE `material` (
    `id`                    INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `name`                  CHAR(30) UNIQUE,
    `type`                  ENUM('staple','vegetable','meat') NOT NULL,
    `unit_price`            FLOAT NOT NULL,
    `availableTime`         INT NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `materialOrder`;
CREATE TABLE `materialOrder` (
    `order_ID`              INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `op_OrderID`            INT UNSIGNED NOT NULL,
    `op_storageID`          INT UNSIGNED DEFAULT NULL,
    `materialID`            INT UNSIGNED NOT NULL,
    `amount`                FLOAT NOT NULL,

    FOREIGN KEY(`materialID`) REFERENCES material(`id`) ON UPDATE CASCADE ON DELETE SET NULL,
    FOREIGN KEY(`op_OrderID`) REFERENCES OperationRecord(operationId) ON UPDATE CASCADE ON DELETE SET NULL,
    FOREIGN KEY(`op_StorageID`) REFERENCES OperationRecord(operationId) ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `stall`;
CREATE TABLE `stall`(
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

DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff`(
    `staff_id`              INT UNSIGNED AUTO_INCREMENT,
    `staff_name`            CHAR(30) NOT NULL,
    `staff_category`        ENUM('NULL') DEFAULT NULL,
    -- 需要补充工作类型
    `Effe_work_time_starts` DATETIME,
    `Effe_work_time_end`    DATETIME,

    PRIMARY KEY ( `staff_id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `Schedule_record`;
CREATE TABLE `Schedule_record`(
    `Schedule_id`            INT UNSIGNED AUTO_INCREMENT,
    `op_OrderID`            INT UNSIGNED NOT NULL,
    `staff_id`              INT UNSIGNED NOT NULL,
    `work_time_start`       DATETIME NOT NULL,
    `work_time_end`         DATETIME NOT NULL,

    FOREIGN KEY(`op_OrderID`) REFERENCES OperationRecord(operationId) ON UPDATE CASCADE ON DELETE SET NULL,
    FOREIGN KEY(`staff_id`) REFERENCES staff(`staff_id`) ON UPDATE CASCADE ON DELETE SET NULL,
    PRIMARY KEY (`Schedule_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `Recipes`;
CREATE TABLE `ACCESS`(
    `position`              VARCHAR(20) PRIMARY KEY,
    `AccesstoOrder`         TINYINT(1),
    `AccesstoStaff`         TINYINT(1),
    `AccesstoStock`         TINYINT(1)
);

DROP TABLE IF EXISTS `OperationRecord`;
CREATE TABLE `OperationRecord`(
    `operationId`           INT UNSIGNED PRIMARY KEY,
    `staffId`               INT UNSIGNED NOT NULL,
    `operationType`         ENUM( 'Pull', 'Order', 'DayShift', 'StallChange'),
    -- Pull 对应？？
    -- Order 对应 materialOrder.op_OrderID
    -- DayShift 对应 Schedule_record.op_OrderID
    `note`                  CHAR(255),-- 备注
    `operationTime`         DATETIME,
    `willSendUpdateMessage` TINYINT(1),-- checkbit Of 推送通知

    FOREIGN KEY(`staffId`) REFERENCES AccountInfo(`staffId`) ON UPDATE CASCADE ON DELETE RESTRICT
);

DROP TABLE IF EXISTS `AccountInfo`;
CREATE TABLE `AccountInfo`(
    `staffId`               INT UNSIGNED PRIMARY KEY,
    `position`              VARCHAR(20) NOT NULL,
    `accountName`           VARCHAR(20) NOT NULL,
    `password`              VARCHAR(20),

    FOREIGN KEY (`position`) REFERENCES `ACCESS`(`position`)
);