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
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
    `email`             VARCHAR(63),
    `display_name`      VARCHAR(31),
    `first_name`        VARCHAR(15),
    `last_name`         VARCHAR(15),
    `slug`              VARCHAR(32),
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

CREATE TABLE `user_social`
(
    `id`                BIGINT    NOT NULL AUTO_INCREMENT,
    `user_id`           BIGINT,
    `social_network_id` BIGINT,
    `link`              VARCHAR(255),
    `created_at`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_at`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `social_network`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(255) NOT NULL,
    `protocol`   VARCHAR(10)  NOT NULL,
    `domain`     VARCHAR(255) NOT NULL,
    `icon`       VARCHAR(255),
    `created_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
