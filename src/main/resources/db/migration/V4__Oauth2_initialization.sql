#TODO remove unused oauth tables

DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`
(
    `client_id`               VARCHAR(255),
    `resource_ids`            VARCHAR(255),
    `client_secret`           VARCHAR(255),
    `scope`                   VARCHAR(255),
    `authorized_grant_types`  VARCHAR(255),
    `web_server_redirect_uri` VARCHAR(255),
    `authorities`             VARCHAR(255),
    `access_token_validity`   INT(11),
    `refresh_token_validity`  INT(11),
    `additional_information`  VARCHAR(63),
    `autoapprove`             VARCHAR(31)
) ENGINE = InnoDB;

DROP TABLE IF EXISTS oauth_client_token;
CREATE TABLE oauth_client_token
(
    token_id          VARCHAR(255),
    token             LONG BINARY,
    authentication_id VARCHAR(255) PRIMARY KEY,
    user_name         VARCHAR(255),
    client_id         VARCHAR(255)
);

drop table if exists oauth_access_token;
create table oauth_access_token
(
    token_id          VARCHAR(255),
    token             LONG BINARY,
    authentication_id VARCHAR(255) PRIMARY KEY,
    user_name         VARCHAR(255),
    client_id         VARCHAR(255),
    authentication    LONG BINARY,
    refresh_token     VARCHAR(255)
);

drop table if exists oauth_refresh_token;
create table oauth_refresh_token
(
    token_id       VARCHAR(255),
    token          LONG BINARY,
    authentication LONG BINARY
);

drop table if exists oauth_code;
create table oauth_code
(
    code           VARCHAR(255),
    authentication LONG BINARY
);

drop table if exists oauth_approvals;
create table oauth_approvals
(
    userId         VARCHAR(255),
    clientId       VARCHAR(255),
    scope          VARCHAR(255),
    status         VARCHAR(10),
    expiresAt      TIMESTAMP,
    lastModifiedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

drop table if exists ClientDetails;
create table ClientDetails
(
    appId                  VARCHAR(255) PRIMARY KEY,
    resourceIds            VARCHAR(255),
    appSecret              VARCHAR(255),
    scope                  VARCHAR(255),
    grantTypes             VARCHAR(255),
    redirectUrl            VARCHAR(255),
    authorities            VARCHAR(255),
    access_token_validity  INTEGER,
    refresh_token_validity INTEGER,
    additionalInformation  VARCHAR(4096),
    autoApproveScopes      VARCHAR(255)
);

INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`,
                                    `web_server_redirect_uri`, `authorities`, `access_token_validity`,
                                    `refresh_token_validity`, `additional_information`, `autoapprove`)
#TODO change getpostman url to valid
VALUES ('client', 'resource-server-rest-api', '$2a$08$X5pFdPxOBbiqPiYoTmyn3O32y/6B/78fMwsBe1ilsQ3K3gzlL0S8e',
        'read,write', 'password,refresh_token',
        'https://www.getpostman.com/oauth2/callback', 'USER', 10800, 0, null, null);
