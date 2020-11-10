DROP IF EXISTS users;
DROP IF EXISTS authorities;
DROP IF EXISTS authority;

CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL,
  is_account_non_expired BOOLEAN NOT NULL,
  is_account_non_locked BOOLEAN NOT NULL,
  is_credentials_non_expired BOOLEAN NOT NULL,
  is_enabled BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS authorities (
  name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS authority (
  user_id INT NOT NULL,
  authority_name VARCHAR(50) NOT NULL
);

ALTER TABLE authority
    ADD FOREIGN KEY (user_id) 
    REFERENCES users(id);
    
ALTER TABLE authority
    ADD FOREIGN KEY (authority_name) 
    REFERENCES authorities(name);