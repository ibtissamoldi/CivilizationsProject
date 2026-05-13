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

    winner VARCHAR(20) NOT NULL DEFAULT 'Enemy',

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

CREATE TABLE civilization_attack_stats (
    civilization_id INT not null,
    num_battle INT not null,
    type VARCHAR(50),

    initial INT,
    drops INT,

    PRIMARY KEY (civilization_id, num_battle, type),
    FOREIGN KEY (civilization_id, num_battle)
        REFERENCES battle_stats(civilization_id, num_battle)
    ON DELETE CASCADE
);

CREATE TABLE civilization_defense_stats (
    civilization_id INT not null,
    num_battle INT not null,
    type VARCHAR(50),

    initial INT,
    drops INT,

    PRIMARY KEY (civilization_id, num_battle, type),
    FOREIGN KEY (civilization_id, num_battle)
        REFERENCES battle_stats(civilization_id, num_battle)
    ON DELETE CASCADE
);

CREATE TABLE civilization_special_stats (
    civilization_id INT not null,
    num_battle INT not null,
    type VARCHAR(50) not null,

    initial INT NOT NULL DEFAULT 0,
    drops INT NOT NULL DEFAULT 0,

    PRIMARY KEY (civilization_id, num_battle, type),
    FOREIGN KEY (civilization_id, num_battle)
        REFERENCES battle_stats(civilization_id, num_battle)
    ON DELETE CASCADE
);

CREATE TABLE enemy_attack_stats (
    civilization_id INT not null,
    num_battle INT not null,
    type VARCHAR(50) not null,

    initial INT NOT NULL DEFAULT 0,
    drops INT NOT NULL DEFAULT 0,

    PRIMARY KEY (civilization_id, num_battle, type),
    FOREIGN KEY (civilization_id, num_battle)
        REFERENCES battle_stats(civilization_id, num_battle)
    ON DELETE CASCADE
);