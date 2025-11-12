CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  token VARCHAR(125)
);

INSERT INTO users (name, password, token) VALUES ('admin', '$2a$10$UdZCpphA6roE2oRH6RfDyeLuyzpSA6FK2PnsH0tqNMp5amp3u9qfG', 'eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiaWF0IjoxNzYyOTg3ODQ3fQ.t5EYAh7QMtDZkhkj0hS5eHjTXXJh6x-OZ70hyUTIqEU');
INSERT INTO users (name, password, token) VALUES ('user', '$2a$10$yrOT//xP6tRbz0TxK23mIegv9DosmEPx9tJ4ldKjX5lGCAvUebUUq', 'eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MiwiaWF0IjoxNzYyOTg3ODc4fQ.ksjQFddxL1_JcaYX_WcZI8agKClVVPpatntTlcRDvA0');

CREATE TABLE movies (
  id SERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  director VARCHAR(255),
  year SMALLINT,
  genre TEXT[],
  synopsis TEXT
);

INSERT INTO movies (title, director, year, genre, synopsis) VALUES
('Inception', 'Christopher Nolan', 2010, ARRAY['Action', 'Sci-Fi', 'Thriller'], 'A thief who steals corporate secrets through dream-sharing technology is given a chance to erase his criminal past.'),
('The Godfather', 'Francis Ford Coppola', 1972, ARRAY['Crime', 'Drama'], 'The aging patriarch of an organized crime dynasty transfers control of his empire to his reluctant son.'),
('Pulp Fiction', 'Quentin Tarantino', 1994, ARRAY['Crime', 'Drama'], 'The lives of two hitmen, a boxer, a gangster and his wife intertwine in tales of violence and redemption.'),
('The Dark Knight', 'Christopher Nolan', 2008, ARRAY['Action', 'Crime', 'Drama'], 'Batman faces the Joker, a criminal mastermind who plunges Gotham City into chaos.'),
('Forrest Gump', 'Robert Zemeckis', 1994, ARRAY['Drama', 'Romance'], 'The presidencies of Kennedy and Johnson, Vietnam, Watergate, and more through the perspective of an Alabama man.'),
('The Matrix', 'Lana Wachowski, Lilly Wachowski', 1999, ARRAY['Action', 'Sci-Fi'], 'A computer hacker learns about the true nature of reality and his role in the war against its controllers.'),
('Fight Club', 'David Fincher', 1999, ARRAY['Drama'], 'An insomniac office worker and a soap maker form an underground fight club that evolves into something much more.'),
('The Shawshank Redemption', 'Frank Darabont', 1994, ARRAY['Drama'], 'Two imprisoned men bond over years, finding solace and eventual redemption through acts of common decency.'),
('Interstellar', 'Christopher Nolan', 2014, ARRAY['Adventure', 'Drama', 'Sci-Fi'], 'A team of explorers travel through a wormhole in space in an attempt to ensure humanity''s survival.'),
('Parasite', 'Bong Joon Ho', 2019, ARRAY['Comedy', 'Drama', 'Thriller'], 'A poor family schemes to become employed by a wealthy household and infiltrate their lives.');

CREATE TABLE reviews (
  id SERIAL PRIMARY KEY,
  movie_id INT NOT NULL,
  username VARCHAR(100) NOT NULL,
  title VARCHAR(100) NOT NULL,
  rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
  description VARCHAR(250),

  FOREIGN KEY (movie_id) REFERENCES movies(id),
  FOREIGN KEY (username) REFERENCES users(name)
);
