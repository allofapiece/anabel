INSERT INTO `user` (`id`, `email`, `display_name`, `first_name`, `last_name`, `slug`, `status`, `created_at`, `updated_at`)
VALUES (1, 'anabel.pinwheel@gmail.com', 'Admin', 'Stas', 'Listratenko', 'anabel-pinwheel', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `password` (`id`, `user_id`, `value`, `status`, `created_at`)
VALUES (1, 1, '$2a$08$X5pFdPxOBbiqPiYoTmyn3O32y/6B/78fMwsBe1ilsQ3K3gzlL0S8e', 'ACTIVE', CURRENT_TIMESTAMP);

INSERT INTO `user_role` (`user_id`, `roles`)
VALUES (1, 'USER'),
       (1, 'ADMIN');

INSERT INTO `site_setting` (`key`, `value`, `type`, `status`)
VALUES ('slugTakenKeywords', 'user shop', 'ARRAY_LIST', 'ACTIVE');

INSERT INTO `social_network` (`name`, `protocol`, `domain`, `icon`)
VALUES ('vk', 'https', 'vk.com', 'fa-vk'),
       ('facebook', 'https', 'facebook.com', 'fa-facebook-f'),
       ('twitter', 'https', 'twitter.com', 'fa-twitter'),
       ('pinterest', 'https', 'pinterest.com', 'fa-pinterest'),
       ('instagram', 'https', 'instagram.com', 'fa-instagram');


