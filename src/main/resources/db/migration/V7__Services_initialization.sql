CREATE TABLE `client`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT,
    `user_id`         BIGINT,
    `product_id`         BIGINT,
    `description`       VARCHAR(255),
    `status`            VARCHAR(50)        DEFAULT 'ACTIVE',
    `created_at`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_at`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

ALTER TABLE `client`
    ADD CONSTRAINT `fk-client-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `client`
    ADD CONSTRAINT `fk-client-product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);
