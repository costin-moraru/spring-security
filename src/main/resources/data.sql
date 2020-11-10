INSERT INTO authorities (name) VALUES
	('ROLE_ADMIN'),
	('ROLE_USER');

INSERT INTO users (id, username, password, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled) VALUES
  	('1', 'admin', '$2y$10$C1/fGlUFnHWmcfzSIE7QlujxNlg5cSJsQVpCK5j/1njLuLhMH3AfG', 'true', 'true', 'true', 'true'),
	('2', 'user', '$2y$10$VfutSYjB.cm1mfLWtlsTkufN6O7ARswnlCrZVUGFlQhg0oFXF3YZ6', 'true', 'true', 'true', 'true');
	
INSERT INTO authority (user_id, authority_name) VALUES
  	('1', 'ROLE_ADMIN'),
	('1', 'ROLE_USER'),
	('2', 'ROLE_USER');
	
	