CREATE TABLE `user_setting`
(
    `id`                       BIGINT NOT NULL AUTO_INCREMENT,
    `user_id`                  BIGINT,
    `allowed_setting_value_id` BIGINT,
    `setting_id`               BIGINT,
    `unconstrained_value`      VARCHAR(255),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `allowed_setting_value`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT,
    `setting_id` BIGINT,
    `caption`    VARCHAR(255),
    `value`      VARCHAR(255),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `city`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT,
    `country_id` BIGINT,
    `name`       VARCHAR(255),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `commitment`
(
    `id`          BIGINT    NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(255),
    `description` VARCHAR(255),
    `created_at`  TIMESTAMP NOT NULL NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_at`  TIMESTAMP NOT NULL      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `company`
(
    `id`         BIGINT    NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(255),
    `status`     VARCHAR(255)            DEFAULT 'ACTIVE',
    `created_at` TIMESTAMP NOT NULL NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `country`
(
    `id`   BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `establishment`
(
    `id`           BIGINT NOT NULL AUTO_INCREMENT,
    `name`         VARCHAR(255),
    `abbreviation` VARCHAR(255),
    `address`      VARCHAR(255),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `faculty`
(
    `id`               BIGINT    NOT NULL AUTO_INCREMENT,
    `establishment_id` BIGINT,
    `name`             VARCHAR(255),
    `created_at`       TIMESTAMP NOT NULL NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_at`       TIMESTAMP NOT NULL      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `feedback`
(
    `id`        BIGINT NOT NULL AUTO_INCREMENT,
    `author_id` BIGINT,
    `target_id` BIGINT,
    `text`      VARCHAR(255),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `group`
(
    `id`            BIGINT    NOT NULL AUTO_INCREMENT,
    `speciality_id` BIGINT,
    `number`        VARCHAR(255),
    `created_at`    TIMESTAMP NOT NULL NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_at`    TIMESTAMP NOT NULL      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `image`
(
    `id`         BIGINT    NOT NULL AUTO_INCREMENT,
    `path`       VARCHAR(255),
    `filename`   VARCHAR(255),
    `extension`  VARCHAR(255),
    `created_at` TIMESTAMP NOT NULL NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `message`
(
    `id`          BIGINT    NOT NULL AUTO_INCREMENT,
    `author_id`   BIGINT,
    `receiver_id` BIGINT,
    `text`        VARCHAR(255),
    `unread`      BIT,
    `status`      VARCHAR(255)            DEFAULT 'ACTIVE',
    `created_at`  TIMESTAMP NOT NULL NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_at`  datetime,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `notification`
(
    `id`         BIGINT    NOT NULL AUTO_INCREMENT,
    `user_id`    BIGINT,
    `title`      VARCHAR(255),
    `message`    VARCHAR(255),
    `created_at` TIMESTAMP NOT NULL NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `order`
(
    `id`          BIGINT    NOT NULL AUTO_INCREMENT,
    `user_id`     BIGINT,
    `title`       VARCHAR(255),
    `description` VARCHAR(255),
    `price`       DECIMAL(19, 2),
    `deadline`    TIMESTAMP NOT NULL      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `status`      VARCHAR(255)            DEFAULT 'ACTIVE',
    `created_at`  TIMESTAMP NOT NULL NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_at`  TIMESTAMP NOT NULL      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `order_comment`
(
    `id`         BIGINT    NOT NULL AUTO_INCREMENT,
    `user_id`    BIGINT,
    `order_id`   BIGINT,
    `text`       VARCHAR(255),
    `created_at` TIMESTAMP NOT NULL NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `order_view`
(
    `id`         BIGINT    NOT NULL AUTO_INCREMENT,
    `order_id`   BIGINT,
    `user_id`    BIGINT,
    `created_at` TIMESTAMP NOT NULL NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `password`
(
    `id`         BIGINT    NOT NULL AUTO_INCREMENT,
    `user_id`    BIGINT,
    `value`      VARCHAR(255),
    `status`     VARCHAR(50)        DEFAULT 'ACTIVE',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `rating`
(
    `id`        BIGINT NOT NULL AUTO_INCREMENT,
    `author_id` BIGINT,
    `target_id` BIGINT,
    `value`     integer,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `section`
(
    `id`          BIGINT    NOT NULL AUTO_INCREMENT,
    `parent_id`   BIGINT,
    `name`        VARCHAR(255),
    `slug`        VARCHAR(255),
    `description` VARCHAR(255),
    `icon`        VARCHAR(255),
    `status`      VARCHAR(255)            DEFAULT 'ACTIVE',
    `created_at`  TIMESTAMP NOT NULL NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_at`  TIMESTAMP NOT NULL      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `setting`
(
    `id`          BIGINT NOT NULL,
    `section_id`  BIGINT,
    `data_type`   VARCHAR(255),
    `constrained` BIT,
    `description` VARCHAR(255),
    `max_value`   double precision,
    `min_value`   double precision,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `setting_section`
(
    `id`   BIGINT NOT NULL,
    `name` VARCHAR(255),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `site_setting`
(
    `id`         BIGINT      NOT NULL AUTO_INCREMENT,
    `key`        VARCHAR(255) UNIQUE,
    `value`      TEXT,
    `type`       VARCHAR(50) NOT NULL DEFAULT 'STRING',
    `status`     VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    `created_at` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `speciality`
(
    `id`         BIGINT    NOT NULL,
    `faculty_id` BIGINT,
    `name`       VARCHAR(255),
    `created_at` TIMESTAMP NOT NULL NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `tag`
(
    `id`         BIGINT    NOT NULL,
    `author_id`  BIGINT,
    `name`       VARCHAR(255),
    `created_at` TIMESTAMP NOT NULL NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `thumbnail`
(
    `id`       BIGINT NOT NULL,
    `image_id` BIGINT,
    `path`     VARCHAR(255),
    `alias`    VARCHAR(255),
    `fit`      VARCHAR(255),
    `height`   double precision,
    `width`    double precision,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `upload`
(
    `id`          BIGINT    NOT NULL,
    `user_id`     BIGINT,
    `title`       VARCHAR(255),
    `description` VARCHAR(255),
    `extension`   VARCHAR(255),
    `mime`        VARCHAR(255),
    `size`        BIGINT,
    `status`      VARCHAR(255)            DEFAULT 'ACTIVE',
    `created_at`  TIMESTAMP NOT NULL NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_at`  TIMESTAMP NOT NULL      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `user`
(
    `id`                BIGINT    NOT NULL AUTO_INCREMENT,
    `image_id`          BIGINT,
    `confirmation_code` VARCHAR(255),
    `email`             VARCHAR(255),
    `display_name`      VARCHAR(255),
    `first_name`        VARCHAR(255),
    `last_name`         VARCHAR(255),
    `about`             TEXT,
    `status`            VARCHAR(50)        DEFAULT 'ACTIVE',
    `created_at`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_at`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `verification_token`
(
    `id`      BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT,
    `token`   VARCHAR(255),
    `expire`  TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `company_user`
(
    `company_id` BIGINT NOT NULL,
    `user_id`    BIGINT NOT NULL,
    PRIMARY KEY (`company_id`, `user_id`)
) ENGINE = InnoDB;

CREATE TABLE `company_order`
(
    `company_id` BIGINT NOT NULL,
    `order_id`   BIGINT NOT NULL,
    PRIMARY KEY (`company_id`, `order_id`)
) ENGINE = InnoDB;

CREATE TABLE `hibernate_sequence`
(
    `next_val` BIGINT
) ENGINE = InnoDB;

CREATE TABLE `order_commitment`
(
    `order_id`      BIGINT NOT NULL,
    `commitment_id` BIGINT NOT NULL,
    PRIMARY KEY (`order_id`, `commitment_id`)
) ENGINE = InnoDB;

CREATE TABLE `order_tag`
(
    `order_id` BIGINT NOT NULL,
    `tag_id`   BIGINT NOT NULL,
    PRIMARY KEY (`order_id`, `tag_id`)
) ENGINE = InnoDB;

CREATE TABLE `order_upload`
(
    `order_id`  BIGINT NOT NULL,
    `upload_id` BIGINT NOT NULL,
    PRIMARY KEY (`order_id`, `upload_id`)
) ENGINE = InnoDB;


CREATE TABLE `user_role`
(
    `user_id` BIGINT NOT NULL,
    `roles`   VARCHAR(255)
) ENGINE = InnoDB;

ALTER TABLE `user`
    ADD CONSTRAINT `fk-user-image_id` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`);
ALTER TABLE `password`
    ADD CONSTRAINT `fk-password-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `verification_token`
    ADD CONSTRAINT `fk-verification_token-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `user_setting`
    ADD CONSTRAINT `fk-user_setting-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `user_setting`
    ADD CONSTRAINT `fk-user_setting-allowed_setting_value_id` FOREIGN KEY (`allowed_setting_value_id`) REFERENCES `allowed_setting_value` (`id`);
ALTER TABLE `user_setting`
    ADD CONSTRAINT `fk-user_setting-setting_id` FOREIGN KEY (`setting_id`) REFERENCES `setting` (`id`);
ALTER TABLE `allowed_setting_value`
    ADD CONSTRAINT `fk-allowed_setting_value-setting_id` FOREIGN KEY (`setting_id`) REFERENCES `setting` (`id`);
ALTER TABLE `city`
    ADD CONSTRAINT `fk-city-country_id` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`);
ALTER TABLE `faculty`
    ADD CONSTRAINT `fk-faculty-establishment_id` FOREIGN KEY (`establishment_id`) REFERENCES `establishment` (`id`);
ALTER TABLE `feedback`
    ADD CONSTRAINT `fk-feedback-author_id` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`);
ALTER TABLE `feedback`
    ADD CONSTRAINT `fk-feedback-target_id` FOREIGN KEY (`target_id`) REFERENCES `user` (`id`);
ALTER TABLE `group`
    ADD CONSTRAINT `fk-group-speciality_id` FOREIGN KEY (`speciality_id`) REFERENCES `speciality` (`id`);
ALTER TABLE `message`
    ADD CONSTRAINT `fk-message-author_id` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`);
ALTER TABLE `message`
    ADD CONSTRAINT `fk-message-receiver_id` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`);
ALTER TABLE `notification`
    ADD CONSTRAINT `fk-notification-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `order`
    ADD CONSTRAINT `fk-order-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `order_comment`
    ADD CONSTRAINT `fk-order_comment-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `order_comment`
    ADD CONSTRAINT `fk-order_comment-order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`);
ALTER TABLE `order_view`
    ADD CONSTRAINT `fk-order_view-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `order_view`
    ADD CONSTRAINT `fk-order_view-order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`);
ALTER TABLE `rating`
    ADD CONSTRAINT `fk-rating-author_id` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`);
ALTER TABLE `rating`
    ADD CONSTRAINT `fk-rating-target_id` FOREIGN KEY (`target_id`) REFERENCES `user` (`id`);
ALTER TABLE `section`
    ADD CONSTRAINT `fk-section-parent_id` FOREIGN KEY (`parent_id`) REFERENCES `section` (`id`);
ALTER TABLE `setting`
    ADD CONSTRAINT `fk-setting-section_id` FOREIGN KEY (`section_id`) REFERENCES `setting_section` (`id`);
ALTER TABLE `speciality`
    ADD CONSTRAINT `fk-speciality-faculty_id` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`id`);
ALTER TABLE `tag`
    ADD CONSTRAINT `fk-tag-author_id` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`);
ALTER TABLE `thumbnail`
    ADD CONSTRAINT `fk-thumbnail-image_id` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`);
ALTER TABLE `company_user`
    ADD CONSTRAINT `fk-company_user-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `company_user`
    ADD CONSTRAINT `fk-company_user-company_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);
ALTER TABLE `company_order`
    ADD CONSTRAINT `fk-company_order-order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`);
ALTER TABLE `company_order`
    ADD CONSTRAINT `fk-company_order-company-id` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);
ALTER TABLE `order_commitment`
    ADD CONSTRAINT `fk-order_commitment-commitment_id` FOREIGN KEY (`commitment_id`) REFERENCES `commitment` (`id`);
ALTER TABLE `order_commitment`
    ADD CONSTRAINT `fk-order_commitment-order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`);
ALTER TABLE `order_tag`
    ADD CONSTRAINT `fk-order_tag-tag_id` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`);
ALTER TABLE `order_tag`
    ADD CONSTRAINT `fk-order_tag-order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`);
ALTER TABLE `user_role`
    ADD CONSTRAINT `fk-user_role-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

CREATE INDEX `idx-user-display_name` ON `user` (`display_name`);
CREATE INDEX `idx-password-user_id` ON `password` (`user_id`);
CREATE INDEX `idx-user_setting-user_id` ON `user_setting` (`user_id`);
CREATE INDEX `idx-user_setting-setting_id` ON `user_setting` (`setting_id`);
CREATE INDEX `idx-city-name` ON `city` (`name`);
CREATE INDEX `idx-commitment-name` ON `commitment` (`name`);
CREATE INDEX `idx-company-name` ON `company` (`name`);
# CREATE INDEX `idx-company_user-company_id`        ON `company_user` (`company_id`);
# CREATE INDEX `idx-company_user-user_id`           ON `company_user` (`user_id`);
# CREATE INDEX `idx-company_order-company_id`       ON `company_order` (`company_id`);
# CREATE INDEX `idx-company_order-user_id`          ON `company_order` (`order_id`);
CREATE INDEX `idx-country-name` ON `country` (`name`);
CREATE INDEX `idx-establishment-name` ON `establishment` (`name`);
CREATE INDEX `idx-establishment-abbreviation` ON `establishment` (`abbreviation`);
CREATE INDEX `idx-faculty-establishment_id` ON `faculty` (`establishment_id`);
CREATE INDEX `idx-faculty-name` ON `faculty` (`name`);
CREATE INDEX `idx-feedback-target_id` ON `feedback` (`target_id`);
CREATE INDEX `idx-group-number` ON `group` (`number`);
CREATE INDEX `idx-group-speciality_id` ON `group` (`speciality_id`);
CREATE INDEX `idx-message-author_id` ON `message` (`author_id`);
CREATE INDEX `idx-message-receiver_id` ON `message` (`receiver_id`);
CREATE INDEX `idx-notification-user_id` ON `notification` (`user_id`);
CREATE INDEX `idx-order-title` ON `order` (`title`);
CREATE INDEX `idx-order-description` ON `order` (`description`);
CREATE INDEX `idx-order-price` ON `order` (`price`);
CREATE INDEX `idx-order_comment-order_id` ON `order_comment` (`order_id`);
# CREATE INDEX `idx-order_commitment-order_id`      ON `order_commitment` (`order_id`);
# CREATE INDEX `idx-order_commitment-commitment_id` ON `order_commitment` (`commitment_id`);
# CREATE INDEX `idx-order_tag-order_id`             ON `order_tag` (`order_id`);
# CREATE INDEX `idx-order_tag-tag_id`               ON `order_tag` (`tag_id`);
CREATE INDEX `idx-order_upload-order_id` ON `order_upload` (`order_id`);
CREATE INDEX `idx-order_view-order_id` ON `order_view` (`order_id`);
CREATE INDEX `idx-rating-target_id` ON `rating` (`target_id`);
CREATE INDEX `idx-section-parent_id` ON `section` (`parent_id`);
CREATE INDEX `idx-section-name` ON `section` (`name`);
CREATE INDEX `idx-site_setting-key` ON `site_setting` (`key`);
CREATE INDEX `idx-speciality-faculty_id` ON `speciality` (`faculty_id`);
CREATE INDEX `idx-speciality-name` ON `speciality` (`name`);
CREATE INDEX `idx-tag-name` ON `tag` (`name`);
CREATE INDEX `idx-thumbnail-image_id` ON `thumbnail` (`image_id`);
CREATE INDEX `idx-thumbnail-alias` ON `thumbnail` (`alias`);
CREATE INDEX `idx-upload-title` ON `upload` (`title`);
CREATE INDEX `idx-upload-user_id` ON `upload` (`user_id`);
CREATE INDEX `idx-user-email` ON `user` (`email`);
CREATE INDEX `idx-user-confirmation_code` ON `user` (`confirmation_code`);
CREATE INDEX `idx-user_role-user_id` ON `user_role` (`user_id`);

INSERT INTO `user` (`id`, `email`, `display_name`, `status`, `created_at`, `updated_at`)
VALUES (1, 'anabel.pinwheel@gmail.com', 'Admin', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `password` (`id`, `user_id`, `value`, `status`, `created_at`)
VALUES (1, 1, '$2a$08$X5pFdPxOBbiqPiYoTmyn3O32y/6B/78fMwsBe1ilsQ3K3gzlL0S8e', 'ACTIVE', CURRENT_TIMESTAMP);

INSERT INTO `user_role` (`user_id`, `roles`)
VALUES (1, 'USER'),
       (1, 'ADMIN');
