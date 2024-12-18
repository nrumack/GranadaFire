-- Populate games table
INSERT INTO games (game_name)
VALUES 
('Battle Arena'),
('League of Heroes'),
('Galaxy Conquerors'),
('Mystic Quest'),
('Cyber Strike');
DESCRIBE players;
-- Populate teams table
INSERT INTO teams (team_name, game_id)
VALUES 
('Alpha Warriors', 1),
('Beta Brawlers', 1),
('Omega Raiders', 2),
('Delta Defenders', 2),
('Gamma Titans', 3),
('Shadow Hunters', 3),
('Sky Guardians', 4),
('Iron Legion', 4),
('Neon Knights', 5),
('Cyber Crusaders', 5);

-- Populate players table
INSERT INTO players (first_name, last_name, nickname, street, post_number, city, country, email, team_id, game_id)
VALUES 
('John', 'Doe', 'Blaze', '123 Elm St', 1010, 'Springfield', 'USA', 'john.doe@example.com', 1, 1),
('Jane', 'Smith', 'Shadow', '456 Oak St', 2020, 'Lincoln', 'USA', 'jane.smith@example.com', 1, 1),
('Alex', 'Taylor', 'Ace', '789 Maple Ave', 3030, 'Wichita', 'USA', 'alex.taylor@example.com', 2, 1),
('Emily', 'Clark', 'Nova', '321 Pine Rd', 4040, 'Salem', 'USA', 'emily.clark@example.com', 2, 1),
('Michael', 'Brown', 'Bolt', '654 Cedar Ln', 5050, 'Dover', 'USA', 'michael.brown@example.com', 3, 2),
('Sophia', 'Wilson', 'Frost', '987 Birch Ct', 6060, 'Boston', 'USA', 'sophia.wilson@example.com', 3, 2),
('Daniel', 'Lee', 'Crusher', '543 Willow Dr', 7070, 'Atlanta', 'USA', 'daniel.lee@example.com', 4, 2),
('Olivia', 'Martinez', 'Flame', '876 Ash St', 8080, 'Denver', 'USA', 'olivia.martinez@example.com', 4, 2),
('Ethan', 'Davis', 'Vortex', '135 Spruce Blvd', 9090, 'Austin', 'USA', 'ethan.davis@example.com', 5, 3),
('Ava', 'Garcia', 'Saber', '246 Fir Way', 10101, 'Phoenix', 'USA', 'ava.garcia@example.com', 6, 3);

-- Populate staff table
INSERT INTO staff (first_name, last_name, nickname, street, post_number, city, country, email)
VALUES 
('Ryan', 'Green', 'CoachG', '124 Elm St', 1010, 'Springfield', 'USA', 'ryan.green@example.com'),
('Laura', 'Harris', 'ManagerL', '457 Oak St', 2020, 'Lincoln', 'USA', 'laura.harris@example.com'),
('Kevin', 'White', 'RefereeK', '789 Maple Ave', 3030, 'Wichita', 'USA', 'kevin.white@example.com'),
('Sarah', 'Black', 'TrainerS', '321 Pine Rd', 4040, 'Salem', 'USA', 'sarah.black@example.com'),
('Emma', 'Moore', 'AdminE', '654 Cedar Ln', 5050, 'Dover', 'USA', 'emma.moore@example.com');

-- Populate matches table
INSERT INTO matches (match_date, match_result, player_id1, player_id2, team_id1, team_id2)
VALUES 
('2024-12-01 14:00:00', 'Win', 1, 3, 1, 2),
('2024-12-02 15:30:00', 'Loss', 5, 7, 3, 4),
('2024-12-03 16:45:00', 'Draw', 2, 4, 1, 2),
('2024-12-04 18:00:00', 'Win', 9, 10, 5, 6),
('2024-12-05 20:15:00', 'Loss', 6, 8, 3, 4);
