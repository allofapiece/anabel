delete from user_role;
delete from user;
delete from password;

insert into user(id, email, status) values
(1, 'mike@gmail.com', 'ACTIVE'),
(2, 'john@gmail.com', 'ACTIVE');

insert into password(id, user_id, value) values
(1, 1, '$2a$08$7qFRVk0v9tPYbdYEjwxEBuRVTO.23f5ZM9/JE6WxtqO3vbvCwjbh6'),
(2, 2, '$2a$08$7qFRVk0v9tPYbdYEjwxEBuRVTO.23f5ZM9/JE6WxtqO3vbvCwjbh6');

insert into user_role(user_id, roles) values
(1, 'ADMIN'), (1, 'USER'),
(2, 'USER');

ALTER TABLE user AUTO_INCREMENT = 1000;
