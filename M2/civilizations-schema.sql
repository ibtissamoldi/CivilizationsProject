-- DROP DATABASE IF EXISTS civilizations;
-- CREATE DATABASE civilizations;
 
CREATE TABLE civilization_stats (
    civilization_id INT primary key AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,

    wood_amount INT NOT NULL DEFAULT 0,
    iron_amount INT NOT NULL DEFAULT 0,
    food_amount INT NOT NULL DEFAULT 0,
    mana_amount INT NOT NULL DEFAULT 0,

    magicTower_counter INT NOT NULL DEFAULT 0,
    church_counter INT NOT NULL DEFAULT 0,
    farm_counter INT NOT NULL DEFAULT 0,
    smithy_counter INT NOT NULL DEFAULT 0,
    carpentry_counter INT NOT NULL DEFAULT 0,

    technology_defense_level INT NOT NULL DEFAULT 0,
    technology_attack_level INT NOT NULL DEFAULT 0,

    battles_counter INT NOT NULL DEFAULT 0
);

CREATE TABLE attack_units_stats (
    unit_id INT NOT NULL AUTO_INCREMENT,
    civilization_id INT NOT NULL,

    type ENUM('Swordsman', 'Spearman', 'Crossbow', 'Cannon') NOT NULL,
    armor INT NOT NULL DEFAULT 0,
    base_damage INT NOT NULL DEFAULT 0,
    experience INT NOT NULL DEFAULT 0,
    sanctified BOOLEAN NOT NULL DEFAULT FALSE,

    PRIMARY KEY (unit_id),
    FOREIGN KEY (civilization_id) REFERENCES civilization_stats(civilization_id)
    ON DELETE CASCADE
);

CREATE TABLE defense_units_stats (
    unit_id INT NOT NULL AUTO_INCREMENT,
    civilization_id INT not null,

    type ENUM('ArrowTower', 'Catapult', 'RocketLauncherTower') NOT NULL,
    armor INT not null DEFAULT 0,
    base_damage INT not null DEFAULT 0,
    experience INT not null DEFAULT 0,
    sanctified BOOLEAN not null DEFAULT FALSE,

    PRIMARY KEY (unit_id),
    FOREIGN KEY (civilization_id) REFERENCES civilization_stats(civilization_id)
    ON DELETE CASCADE
);


CREATE TABLE special_units_stats (
    unit_id INT not null AUTO_INCREMENT,
    civilization_id INT NOT NULL,

    type ENUM('Magician', 'Priest') NOT NULL,
    armor INT not null DEFAULT 0,
    base_damage INT not null DEFAULT 0,
    experience INT not null DEFAULT 0,

    PRIMARY KEY (unit_id),
    FOREIGN KEY (civilization_id) REFERENCES civilization_stats(civilization_id)
    ON DELETE CASCADE
);



CREATE TABLE battle_stats (
    civilization_id INT not null,
    num_battle INT not null,

    wood_acquired INT NOT NULL DEFAULT 0,
    iron_acquired INT NOT NULL DEFAULT 0,

    winner VARCHAR(50) NOT NULL DEFAULT 'Enemy',
    
    civ_food_cost INT DEFAULT 0,
	civ_wood_cost INT DEFAULT 0,
	civ_iron_cost INT DEFAULT 0,
	
	enemy_food_cost INT DEFAULT 0,
	enemy_wood_cost INT DEFAULT 0,
	enemy_iron_cost INT DEFAULT 0,
	
	civ_food_losses INT DEFAULT 0,
	civ_wood_losses INT DEFAULT 0,
	civ_iron_losses INT DEFAULT 0,
	
	enemy_food_losses INT DEFAULT 0,
	enemy_wood_losses INT DEFAULT 0,
	enemy_iron_losses INT DEFAULT 0,
	
	rubble_wood INT DEFAULT 0,
	rubble_iron INT DEFAULT 0,
	
	battle_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (civilization_id, num_battle),
    FOREIGN KEY (civilization_id) REFERENCES civilization_stats(civilization_id)
    ON DELETE CASCADE
);

CREATE TABLE battle_log (
    civilization_id INT not null,
    num_battle INT not null,
    num_line INT not null,

    log_entry TEXT,

    PRIMARY KEY (civilization_id, num_battle, num_line),
    FOREIGN KEY (civilization_id, num_battle)
        REFERENCES battle_stats(civilization_id, num_battle)
    ON DELETE CASCADE
);



CREATE TABLE battle_units_stats (

    civilization_id INT NOT NULL,
    num_battle INT NOT NULL,

    side ENUM('Player', 'Enemy') NOT NULL,

    unit_category ENUM('Attack', 'Defense', 'Special') NOT NULL,

    type VARCHAR(50) NOT NULL,

    initial_units INT NOT NULL DEFAULT 0,
    dropped_units INT NOT NULL DEFAULT 0,

    PRIMARY KEY(civilization_id, num_battle, side, type),

    FOREIGN KEY (civilization_id, num_battle)
    REFERENCES battle_stats(civilization_id, num_battle)
    ON DELETE CASCADE
);