CREATE TABLE `account`(
    `account_id` bigint(20)   NOT NULL AUTO_INCREMENT,
    `user_user_id` bigint(20)   NOT NULL,
    `fi_type`      varchar(255) DEFAULT NULL,
    `fip_id`     varchar(255) DEFAULT NULL,
    `account_type` varchar(255) DEFAULT NULL,
    `masked_account_number` varchar(255) DEFAULT NULL,
    `link_ref_number` varchar(255) DEFAULT NULL,
    `created_by`    varchar(255) DEFAULT NULL,
    `created_date`  datetime     DEFAULT NULL,
    `modified_by`   varchar(255) DEFAULT NULL,
    `modified_date` datetime     DEFAULT NULL,
    `deleted`       tinyint(1)   DEFAULT '0',
    PRIMARY KEY (`account_id`),
    KEY `created_date_idx` (`created_date`),
    KEY `modified_date_idx` (`modified_date`)
)  ENGINE = InnoDB
   AUTO_INCREMENT = 1995
   DEFAULT CHARSET = utf8;

ALTER TABLE `account` ADD CONSTRAINT fk_account_user FOREIGN KEY (user_user_id) REFERENCES user(user_id);