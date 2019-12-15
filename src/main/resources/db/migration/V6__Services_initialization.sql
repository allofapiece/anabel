CREATE TABLE `service`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT,
    `user_id`         BIGINT,
    `name`       VARCHAR(255),
    `about`       TEXT,
    `status`            VARCHAR(50)        DEFAULT 'ACTIVE',
    `created_at`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_at`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `catalog`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT,
    `user_id`         BIGINT,
    `service_id`         BIGINT,
    `name`       VARCHAR(255),
    `status`            VARCHAR(50)        DEFAULT 'ACTIVE',
    `created_at`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_at`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `product`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT,
    `user_id`         BIGINT,
    `catalog_id`         BIGINT,
    `name`       VARCHAR(255),
    `price` DECIMAL,
    `status`            VARCHAR(50)        DEFAULT 'ACTIVE',
    `created_at`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_at`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

ALTER TABLE `service`
    ADD CONSTRAINT `fk-service-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `catalog`
    ADD CONSTRAINT `fk-catalog-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `catalog`
    ADD CONSTRAINT `fk-catalog-service_id` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`);
ALTER TABLE `product`
    ADD CONSTRAINT `fk-product-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `product`
    ADD CONSTRAINT `fk-product-catalog_id` FOREIGN KEY (`catalog_id`) REFERENCES `catalog` (`id`);
