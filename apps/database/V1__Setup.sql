CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  token VARCHAR(125)
);

INSERT INTO users (name, password) VALUES ('admin', '$2a$10$UdZCpphA6roE2oRH6RfDyeLuyzpSA6FK2PnsH0tqNMp5amp3u9qfG');
