CREATE DATABASE granadafire;
USE granadafire;




CREATE TABLE players(
	player_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    street VARCHAR(50),
    post_number INT,
    city VARCHAR(50),
    country VARCHAR(50),
    email VARCHAR(50),
    team_id INT
);
ALTER TABLE players ADD FOREIGN KEY (team_id) REFERENCES teams(team_id);
ALTER TABLE players DROP FOREIGN KEY team_id;
SET GLOBAL FOREIGN_KEY_CHECKS = 0;
ALTER TABLE players DISABLE KEYS;
ALTER TABLE players ENABLE KEYS;
ALTER TABLE players DROP FOREIGN KEY players_ibfk_5;
ALTER TABLE players DROP FOREIGN KEY players_ibfk_6;
ALTER TABLE players DROP FOREIGN KEY players_ibfk_7;
ALTER TABLE players DROP FOREIGN KEY players_ibfk_4;
ALTER TABLE players ADD CONSTRAINT team_id FOREIGN KEY (team_id) REFERENCES teams(team_id);




CREATE TABLE games (
	game_id INT PRIMARY KEY AUTO_INCREMENT,
    game_name VARCHAR(50),
    player_id int
    );
ALTER TABLE games ADD FOREIGN KEY (player_id) REFERENCES players(player_id);
ALTER TABLE games DROP FOREIGN KEY games_ibfk_1;
ALTER TABLE games ADD CONSTRAINT player_id FOREIGN KEY (player_id) REFERENCES players(player_id);




CREATE TABLE teams(
	team_id INT PRIMARY KEY AUTO_INCREMENT,
    team_name VARCHAR(50),
    game_id int
    );
ALTER TABLE teams ADD FOREIGN KEY (game_id) REFERENCES games(game_id);
ALTER TABLE teams DROP FOREIGN KEY teams_ibfk_1;
ALTER TABLE teams ADD CONSTRAINT game_id FOREIGN KEY (game_id) REFERENCES games(game_id);




CREATE TABLE matches(
	match_id INT PRIMARY KEY AUTO_INCREMENT,
    match_date DATETIME,
    match_result VARCHAR(10),
    player_id1 INT,
    player_id2 INT,
    FOREIGN KEY (player_id1) REFERENCES players(player_id),
    FOREIGN KEY (player_id2) REFERENCES players(player_id),
    team_id1 INT,
    team_id2 INT
    );
ALTER TABLE matches ADD FOREIGN KEY (team_id1) REFERENCES teams(team_id);
ALTER TABLE matches ADD FOREIGN KEY (team_id2) REFERENCES teams(team_id);
ALTER TABLE matches DROP FOREIGN KEY matches_ibfk_1;
ALTER TABLE matches DROP FOREIGN KEY matches_ibfk_2;
ALTER TABLE matches DROP FOREIGN KEY matches_ibfk_3;
ALTER TABLE matches DROP FOREIGN KEY matches_ibfk_4;
ALTER TABLE matches ADD CONSTRAINT team_id1 FOREIGN KEY (team_id1) REFERENCES teams(team_id);
ALTER TABLE matches ADD CONSTRAINT team_id2 FOREIGN KEY (team_id2) REFERENCES teams(team_id);




CREATE TABLE staff(
	staff_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    street VARCHAR(50),
    post_number INT,
    city VARCHAR(50),
    country VARCHAR(50),
    email VARCHAR(50)
);