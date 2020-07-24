CREATE TABLE `user_to_perfios_txn_mapping` (
    `id` bigint(20)   NOT NULL AUTO_INCREMENT,
    `user_user_id` bigint(20)   NOT NULL,
    `txn_id` varchar(255) DEFAULT NULL,
    `type` varchar(255) DEFAULT NULL,
    `created_by`    varchar(255) DEFAULT NULL,
    `created_date`  datetime     DEFAULT NULL,
    `modified_by`   varchar(255) DEFAULT NULL,
    `modified_date` datetime     DEFAULT NULL,
    `deleted`       tinyint(1)   DEFAULT '0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `txn_id` (`txn_id`),
    KEY `created_date_idx` (`created_date`),
    KEY `modified_date_idx` (`modified_date`)
) ENGINE = InnoDB
 AUTO_INCREMENT = 1083
 DEFAULT CHARSET = utf8;

ALTER TABLE `user_to_perfios_txn_mapping` ADD CONSTRAINT fk_perfios_txn_details FOREIGN KEY (user_user_id) REFERENCES user(user_id);
