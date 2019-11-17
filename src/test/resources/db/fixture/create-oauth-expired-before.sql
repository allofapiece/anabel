delete
from oauth_client_details;
delete
from oauth_access_token;

INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`,
                                    `web_server_redirect_uri`, `authorities`, `access_token_validity`,
                                    `refresh_token_validity`, `additional_information`, `autoapprove`)
VALUES ('client', 'resource-server-rest-api', '$2a$08$X5pFdPxOBbiqPiYoTmyn3O32y/6B/78fMwsBe1ilsQ3K3gzlL0S8e',
        'read,write', 'password,refresh_token',
        'https://www.getpostman.com/oauth2/callback', 'USER', 1, 0, null, null);

ALTER TABLE oauth_client_details
    AUTO_INCREMENT = 10;
