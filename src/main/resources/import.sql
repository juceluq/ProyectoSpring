INSERT INTO role (name) VALUES ('ROLE_ADMIN') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO role (name) VALUES ('ROLE_USER') ON DUPLICATE KEY UPDATE name = VALUES(name);
