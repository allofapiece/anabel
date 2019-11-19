ALTER TABLE `user`
    ADD CONSTRAINT `fk-user-image_id` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`);
ALTER TABLE `password`
    ADD CONSTRAINT `fk-password-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE `verification_token`
    ADD CONSTRAINT `fk-verification_token-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE ON DELETE CASCADE;
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
ALTER TABLE `user_social`
    ADD CONSTRAINT `fk-user_social-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `user_social`
    ADD CONSTRAINT `fk-user_social-social_network_id` FOREIGN KEY (`social_network_id`) REFERENCES `social_network` (`id`);
