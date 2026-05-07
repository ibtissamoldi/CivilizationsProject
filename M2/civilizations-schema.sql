-- DROP DATABASE IF EXISTS civilizations;
-- SCHEMA EN MYSQL, igual falta adaptar a oracle
-- CREATE DATABASE civilizations;
 -- falta mirar el auto-increment en las  primary key
 
CREATE TABLE civilization_stats (
    civilization_id INT PRIMARY KEY NOT NULL,
    name VARCHAR(100),

    wood_amount INT,
    iron_amount INT,
    food_amount INT,
    mana_amount INT,

    magicTower_counter INT,
    church_counter INT,
    farm_counter INT,
    smithy_counter INT,
    carpentry_counter INT,

    technology_defense_level INT,
    technology_attack_level INT,

    battles_counter INT
);

CREATE TABLE attack_units_stats (
    civilization_id INT NOT NULL,
    unit_id INT,

    type ENUM('Swordsman', 'Spearman', 'Crossbow', 'Cannon') NOT NULL,
    armor INT,
    base_damage INT,
    experience INT,
    sanctified BOOLEAN,

    PRIMARY KEY (civilization_id, unit_id),
    FOREIGN KEY (civilization_id) REFERENCES civilization_stats(civilization_id)
);


CREATE TABLE special_units_stats (
    civilization_id INT NOT NULL,
    unit_id INT,

    type ENUM('Magician', 'Priest') NOT NULL,
    armor INT,
    base_damage INT,
    experience INT,
    

    PRIMARY KEY (civilization_id, unit_id),
    FOREIGN KEY (civilization_id) REFERENCES civilization_stats(civilization_id)
);

CREATE TABLE defense_units_stats (
    civilization_id INT NOT NULL,
    unit_id INT,

    type ENUM('ArrowTower', 'Catapult', 'RocketLauncherTower') NOT NULL,
    armor INT,
    base_damage INT,
    experience INT,
    sanctified BOOLEAN,

    PRIMARY KEY (civilization_id, unit_id),
    FOREIGN KEY (civilization_id) REFERENCES civilization_stats(civilization_id)
);

CREATE TABLE battle_stats (
    civilization_id INT not null,
    num_battle INT not null,

    wood_acquired INT,
    iron_acquired INT,

    PRIMARY KEY (civilization_id, num_battle),
    FOREIGN KEY (civilization_id) REFERENCES civilization_stats(civilization_id)
);

CREATE TABLE battle_log (
    civilization_id INT not null,
    num_battle INT not null,
    num_line INT not null,

    log_entry TEXT,

    PRIMARY KEY (civilization_id, num_battle, num_line),
    FOREIGN KEY (civilization_id, num_battle)
        REFERENCES battle_stats(civilization_id, num_battle)
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
);

CREATE TABLE civilization_special_stats (
  civilization_id INT not null,
    num_battle INT not null,
    type VARCHAR(50),

    initial INT,
    drops INT,

    PRIMARY KEY (civilization_id, num_battle, type),
    FOREIGN KEY (civilization_id, num_battle)
        REFERENCES battle_stats(civilization_id, num_battle)
);

CREATE TABLE enemy_attack_stats (
    civilization_id INT not null,
    num_battle INT not null,
    type VARCHAR(50),

    initial INT,
    drops INT,

    PRIMARY KEY (civilization_id, num_battle, type),
    FOREIGN KEY (civilization_id, num_battle)
        REFERENCES battle_stats(civilization_id, num_battle)
);