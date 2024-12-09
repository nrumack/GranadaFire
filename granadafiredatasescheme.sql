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
    team_id INT,
	FOREIGN KEY (team_id) REFERENCES teams(team_id)
);

CREATE TABLE games (
	game_id INT PRIMARY KEY AUTO_INCREMENT,
    game_name VARCHAR(50),
    player_id int,
    FOREIGN KEY (player_id) REFERENCES players(player_id)
);

CREATE TABLE teams(
	team_id INT PRIMARY KEY AUTO_INCREMENT,
    team_name VARCHAR(50),
    game_id int,
    FOREIGN KEY (game_id) REFERENCES games(game_id)
);

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

CREATE TABLE matches(
	match_id INT PRIMARY KEY AUTO_INCREMENT,
    match_date DATETIME,
    match_result VARCHAR(10),
    player_id1 INT,
    player_id2 INT,
    FOREIGN KEY (player_id1) REFERENCES players(player_id),
    FOREIGN KEY (player_id2) REFERENCES players(player_id),
    team_id1 INT,
    team_id2 INT,
    FOREIGN KEY (team_id1) REFERENCES teams(team_id),
    FOREIGN KEY (team_id2) REFERENCES teams(team_id)
);