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

-- INSERT INTO movies (title, director, year, genre, synopsis) VALUES
-- ('Inception', 'Christopher Nolan', 2010, ARRAY['Action', 'Sci-Fi', 'Thriller'], 'A thief who steals corporate secrets through dream-sharing technology is given a chance to erase his criminal past.'),
-- ('The Godfather', 'Francis Ford Coppola', 1972, ARRAY['Crime', 'Drama'], 'The aging patriarch of an organized crime dynasty transfers control of his empire to his reluctant son.'),
-- ('Pulp Fiction', 'Quentin Tarantino', 1994, ARRAY['Crime', 'Drama'], 'The lives of two hitmen, a boxer, a gangster and his wife intertwine in tales of violence and redemption.'),
-- ('The Dark Knight', 'Christopher Nolan', 2008, ARRAY['Action', 'Crime', 'Drama'], 'Batman faces the Joker, a criminal mastermind who plunges Gotham City into chaos.'),
-- ('Forrest Gump', 'Robert Zemeckis', 1994, ARRAY['Drama', 'Romance'], 'The presidencies of Kennedy and Johnson, Vietnam, Watergate, and more through the perspective of an Alabama man.'),
-- ('The Matrix', 'Lana Wachowski, Lilly Wachowski', 1999, ARRAY['Action', 'Sci-Fi'], 'A computer hacker learns about the true nature of reality and his role in the war against its controllers.'),
-- ('Fight Club', 'David Fincher', 1999, ARRAY['Drama'], 'An insomniac office worker and a soap maker form an underground fight club that evolves into something much more.'),
-- ('The Shawshank Redemption', 'Frank Darabont', 1994, ARRAY['Drama'], 'Two imprisoned men bond over years, finding solace and eventual redemption through acts of common decency.'),
-- ('Interstellar', 'Christopher Nolan', 2014, ARRAY['Adventure', 'Drama', 'Sci-Fi'], 'A team of explorers travel through a wormhole in space in an attempt to ensure humanity''s survival.'),
-- ('Parasite', 'Bong Joon Ho', 2019, ARRAY['Comedy', 'Drama', 'Thriller'], 'A poor family schemes to become employed by a wealthy household and infiltrate their lives.');

INSERT INTO movies (title, director, year, genre, synopsis) VALUES
('The Godfather', 'Francis Ford Coppola', 1972, ARRAY['Drama', 'Ação'], 'A saga da família Corleone mostra a ascensão e o poder de uma máfia italiana nos EUA.'),
('The Shawshank Redemption', 'Frank Darabont', 1994, ARRAY['Drama'], 'Um homem condenado injustamente forma uma amizade duradoura e busca esperança dentro da prisão de Shawshank.'),
('Pulp Fiction', 'Quentin Tarantino', 1994, ARRAY['Ação', 'Drama'], 'Histórias interligadas de criminosos em Los Angeles repletas de humor negro e diálogos marcantes.'),
('The Dark Knight', 'Christopher Nolan', 2008, ARRAY['Ação', 'Drama'], 'Batman enfrenta o Coringa em uma luta intensa entre justiça e caos em Gotham City.'),
('Forrest Gump', 'Robert Zemeckis', 1994, ARRAY['Drama', 'Romance'], 'A vida extraordinária de um homem simples que testemunha grandes eventos da história americana.'),
('Fight Club', 'David Fincher', 1999, ARRAY['Drama', 'Ação'], 'Um homem insatisfeito cria um clube de luta clandestino que se transforma em uma revolução contra o sistema.'),
('Inception', 'Christopher Nolan', 2010, ARRAY['Ação', 'Ficção Científica', 'Drama'], 'Um ladrão que invade sonhos tenta realizar o golpe perfeito: plantar uma ideia na mente de alguém.'),
('The Matrix', 'Lana Wachowski', 1999, ARRAY['Ação', 'Ficção Científica'], 'Um hacker descobre que o mundo é uma simulação e se une à resistência para libertar a humanidade.'),
('Goodfellas', 'Martin Scorsese', 1990, ARRAY['Drama', 'Ação'], 'A ascensão e queda de um mafioso ao longo de décadas no submundo do crime organizado.'),
('The Lord of the Rings: The Return of the King', 'Peter Jackson', 2003, ARRAY['Ação', 'Aventura', 'Fantasia'], 'A batalha final pela Terra-média decide o destino do Um Anel e de todos os seus povos.'),
('The Empire Strikes Back', 'Irvin Kershner', 1980, ARRAY['Ação', 'Aventura', 'Ficção Científica'], 'O Império persegue os rebeldes enquanto Luke Skywalker treina para se tornar um verdadeiro Jedi.'),
('The Silence of the Lambs', 'Jonathan Demme', 1991, ARRAY['Terror', 'Drama'], 'Uma jovem agente do FBI busca a ajuda de um assassino canibal para capturar outro serial killer.'),
('Saving Private Ryan', 'Steven Spielberg', 1998, ARRAY['Ação', 'Drama'], 'Durante a Segunda Guerra Mundial, soldados arriscam tudo para resgatar um único homem atrás das linhas inimigas.'),
('Schindler’s List', 'Steven Spielberg', 1993, ARRAY['Drama'], 'A história real de um empresário que salvou centenas de judeus durante o Holocausto.'),
('The Green Mile', 'Frank Darabont', 1999, ARRAY['Drama', 'Fantasia'], 'Um guarda de prisão descobre que um prisioneiro condenado à morte possui um dom sobrenatural.'),
('Se7en', 'David Fincher', 1995, ARRAY['Terror', 'Drama'], 'Dois detetives investigam uma série de assassinatos inspirados nos sete pecados capitais.'),
('The Departed', 'Martin Scorsese', 2006, ARRAY['Ação', 'Drama'], 'Um policial infiltrado e um espião da máfia tentam descobrir a identidade um do outro em Boston.'),
('Gladiator', 'Ridley Scott', 2000, ARRAY['Ação', 'Aventura', 'Drama'], 'Um general romano busca vingança contra o imperador que destruiu sua vida e sua família.'),
('Interstellar', 'Christopher Nolan', 2014, ARRAY['Ficção Científica', 'Drama', 'Aventura'], 'Exploradores viajam através de um buraco de minhoca em busca de um novo lar para a humanidade.'),
('Braveheart', 'Mel Gibson', 1995, ARRAY['Ação', 'Aventura', 'Drama'], 'William Wallace lidera os escoceses em uma luta épica pela liberdade contra a opressão inglesa.');

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
