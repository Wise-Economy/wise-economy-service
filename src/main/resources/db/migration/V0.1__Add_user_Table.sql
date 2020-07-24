CREATE TABLE `user` (
 `user_id` bigint(20)   NOT NULL AUTO_INCREMENT,
 `gender`  varchar(255) DEFAULT NULL,
 `dob`     datetime     DEFAULT NULL,
 `mobile_number` varchar(255) NOT NULL,
 `name`    varchar(255) DEFAULT NULL,
 `perfios_user_handle` varchar(255) DEFAULT NULL,
 `perfios_user_id` varchar(255) DEFAULT NULL,
 `perfios_consent_handle` varchar(255) DEFAULT NULL,
 `created_by`    varchar(255) DEFAULT NULL,
 `created_date`  datetime     DEFAULT NULL,
 `modified_by`   varchar(255) DEFAULT NULL,
 `modified_date` datetime     DEFAULT NULL,
 `deleted`       tinyint(1)   DEFAULT '0',
 PRIMARY KEY (`user_id`),
 UNIQUE KEY `user_mobile_number_uk` (`mobile_number`),
 KEY `created_date_idx` (`created_date`),
 KEY `modified_date_idx` (`modified_date`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1083
  DEFAULT CHARSET = utf8;