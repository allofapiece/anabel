delete
from site_setting;

insert into site_setting(id, `key`, value, type, status)
values (1, 'key1', 'value1', 'STRING', 'ACTIVE'),
       (2, 'key2', 'value2', 'STRING', 'ACTIVE'),
       (3, 'key3', 'value3', 'STRING', 'ACTIVE'),
       (4, 'key4', 'value4', 'STRING', 'ACTIVE'),
       (5, 'key5', 'value5', 'STRING', 'ACTIVE'),
       (6, 'key6', 'value6', 'STRING', 'ACTIVE');

ALTER TABLE site_setting
    AUTO_INCREMENT = 1000;
