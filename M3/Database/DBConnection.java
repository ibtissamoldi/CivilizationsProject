package M3.Database;

import java.sql.*;
import java.util.ArrayList;

import M3.game.Civilization;
import M3.interfaces.MilitaryUnit;

import M3.units.AttackUnit;
import M3.units.DefenseUnit;
import M3.units.SpecialUnit;

import M3.units.attack.*;
import M3.units.defense.*;
import M3.units.special.*;

public class DBConnection {

    private Connection conn;


    public void connect() {

    	String[] urls = {"jdbc:mysql://localhost:3306/civilizations?useSSL=false&serverTimezone=UTC","jdbc:mysql://localhost:3307/civilizations?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"};

        String user = "root";
        String password = "12345";
        
        try {
        	
            Class.forName("com.mysql.cj.jdbc.Driver");

            boolean connected = false;
            	
            /*conn = DriverManager.getConnection(
            		"jdbc:mysql://localhost:3307/civilizations?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
            		"civ_user",
            		"bichos2.0");*/
           // System.out.println("Database connected.");

        	for (String url : urls) {

        		try {

                    conn = DriverManager.getConnection(url, user, password);

                    System.out.println("Connected to: " + url);

                    connected = true;

                    break;

                } catch (SQLException e) {

                    System.out.println("Failed: " + url);
                }
            
        	 }

            if (!connected) {

                throw new SQLException("No database available.");
            }
            
            
        } catch (Exception e) {

            System.out.println("Database connection error.");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void closeConnection() {

        try {

            if (conn != null && !conn.isClosed()) {

                conn.close();
                System.out.println("Connection closed.");
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    public void saveCivilization(Civilization civ) {

        try {

            conn.setAutoCommit(false);

            int civId = getCivilizationId(civ.getName());

            if (civId == -1) {

                civId = insertCivilization(civ);

            } else {

                updateCivilization(civ, civId);

                deleteArmy(civId);
            }

            saveArmy(civId, civ.getArmy());

            conn.commit();

            System.out.println("Civilization saved.");

        } catch (Exception e) {

            try {

                conn.rollback();

            } catch (SQLException ex) {

                ex.printStackTrace();
            }

            System.out.println("Error saving civilization.");
            e.printStackTrace();

        } finally {

            try {

                conn.setAutoCommit(true);

            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }



    private int insertCivilization(Civilization civ) throws SQLException {

        String sql = """
                INSERT INTO civilization_stats
                (name, wood_amount, iron_amount, food_amount, mana_amount,
                magicTower_counter, church_counter,
                farm_counter, smithy_counter, carpentry_counter,
                technology_defense_level, technology_attack_level, battles_counter)

                VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)
                """;

        PreparedStatement ps = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, civ.getName());
        ps.setInt(2, civ.getWood());
        ps.setInt(3, civ.getIron());
        ps.setInt(4, civ.getFood());
        ps.setInt(5, civ.getMana());

        ps.setInt(6, civ.getMagicTower());
        ps.setInt(7, civ.getChurch());
        ps.setInt(8, civ.getFarm());
        ps.setInt(9, civ.getSmithy());
        ps.setInt(10, civ.getCarpentry());
        
        ps.setInt(11, civ.getTechnologyDefense());
        ps.setInt(12, civ.getTechnologyAttack());

        ps.setInt(13, civ.getBattles());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) {

            return rs.getInt(1);
        }

        return -1;
    }



    private void updateCivilization(Civilization civ, int civId)
            throws SQLException {

        String sql = """
                UPDATE civilization_stats SET

                wood_amount=?,
                iron_amount=?,
                food_amount=?,
                mana_amount=?,

                magicTower_counter=?,
                church_counter=?,
                farm_counter=?,
                smithy_counter=?,
                carpentry_counter=?,
                
                technology_defense_level=?,
                technology_attack_level=?,

                battles_counter=?

                WHERE civilization_id=?
                """;
        



        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, civ.getWood());
        ps.setInt(2, civ.getIron());
        ps.setInt(3, civ.getFood());
        ps.setInt(4, civ.getMana());

        ps.setInt(5, civ.getMagicTower());
        ps.setInt(6, civ.getChurch());
        ps.setInt(7, civ.getFarm());
        ps.setInt(8, civ.getSmithy());
        ps.setInt(9, civ.getCarpentry());
        
        ps.setInt(10, civ.getTechnologyDefense());
        ps.setInt(11, civ.getTechnologyAttack());
        
        ps.setInt(12, civ.getBattles());

        ps.setInt(13, civId);

        ps.executeUpdate();
    }



    public int getCivilizationId(String name) {

        try {

            String sql =
                    "SELECT civilization_id FROM civilization_stats WHERE name=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getInt("civilization_id");
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return -1;
    }



    public Civilization loadCivilizationComplete(String name) {

        try {

            String sql =
                    "SELECT * FROM civilization_stats WHERE name=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Civilization civ =
                        new Civilization(rs.getString("name"));

                civ.setWood(rs.getInt("wood_amount"));
                civ.setIron(rs.getInt("iron_amount"));
                civ.setFood(rs.getInt("food_amount"));
                civ.setMana(rs.getInt("mana_amount"));

                civ.setTechnologyAttack(
                        rs.getInt("technology_attack_level"));

                civ.setTechnologyDefense(
                        rs.getInt("technology_defense_level"));

                civ.setMagicTower(
                        rs.getInt("magicTower_counter"));

                civ.setChurch(
                        rs.getInt("church_counter"));

                civ.setFarm(
                        rs.getInt("farm_counter"));

                civ.setSmithy(
                        rs.getInt("smithy_counter"));

                civ.setCarpentry(
                        rs.getInt("carpentry_counter"));

                civ.setBattles(
                        rs.getInt("battles_counter"));

                int civId =
                        rs.getInt("civilization_id");

                loadArmy(civId, civ);

                return civ;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }


    public void deleteArmy(int civId) {

        try {

            String sql1 =
                    "DELETE FROM attack_units_stats WHERE civilization_id=?";

            String sql2 =
                    "DELETE FROM defense_units_stats WHERE civilization_id=?";

            String sql3 =
                    "DELETE FROM special_units_stats WHERE civilization_id=?";

            PreparedStatement ps1 =
                    conn.prepareStatement(sql1);

            PreparedStatement ps2 =
                    conn.prepareStatement(sql2);

            PreparedStatement ps3 =
                    conn.prepareStatement(sql3);

            ps1.setInt(1, civId);
            ps2.setInt(1, civId);
            ps3.setInt(1, civId);

            ps1.executeUpdate();
            ps2.executeUpdate();
            ps3.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    public void saveArmy(int civId,
                         ArrayList<MilitaryUnit>[] army) {

        saveAttackUnits(civId, army);

        saveDefenseUnits(civId, army);

        saveSpecialUnits(civId, army);
    }



    public void saveAttackUnits(int civId,
                                ArrayList<MilitaryUnit>[] army) {

        try {

            String sql = """
                    INSERT INTO attack_units_stats
                    (civilization_id, type,
                    armor, base_damage,
                    experience, sanctified)

                    VALUES (?,?,?,?,?,?)
                    """;

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            String[] atkTypes = {
                    "Swordsman",
                    "Spearman",
                    "Crossbow",
                    "Cannon"
            };

            for (int i = 0; i <= 3; i++) {

                if (army[i] == null)
                    continue;

                for (MilitaryUnit unit : army[i]) {

                    AttackUnit au = (AttackUnit) unit;

                    ps.setInt(1, civId);
                    ps.setString(2, atkTypes[i]);

                    ps.setInt(3,
                            au.getInitialArmor());

                    ps.setInt(4,
                            au.getBaseDamage());

                    ps.setInt(5,
                            au.getExperience());

                    ps.setBoolean(6,
                            au.isSanctified());

                    ps.executeUpdate();
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    public void saveDefenseUnits(int civId,
                                 ArrayList<MilitaryUnit>[] army) {

        try {

            String sql = """
                    INSERT INTO defense_units_stats
                    (civilization_id, type,
                    armor, base_damage,
                    experience, sanctified)

                    VALUES (?,?,?,?,?,?)
                    """;

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            String[] defTypes = {
                    "ArrowTower",
                    "Catapult",
                    "RocketLauncherTower"
            };

            for (int i = 4; i <= 6; i++) {

                if (army[i] == null)
                    continue;

                for (MilitaryUnit unit : army[i]) {

                    DefenseUnit du =
                            (DefenseUnit) unit;

                    ps.setInt(1, civId);

                    ps.setString(2,
                            defTypes[i - 4]);

                    ps.setInt(3,
                            du.getInitialArmor());

                    ps.setInt(4,
                            du.getBaseDamage());

                    ps.setInt(5,
                            du.getExperience());

                    ps.setBoolean(6,
                            du.isSanctified());

                    ps.executeUpdate();
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    public void saveSpecialUnits(int civId,
                                 ArrayList<MilitaryUnit>[] army) {

        try {

            String sql = """
                    INSERT INTO special_units_stats
                    (civilization_id, type,
                    armor, base_damage,
                    experience)

                    VALUES (?,?,?,?,?)
                    """;

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            String[] spcTypes = {
                    "Magician",
                    "Priest"
            };

            for (int i = 7; i <= 8; i++) {

                if (army[i] == null)
                    continue;

                for (MilitaryUnit unit : army[i]) {

                    SpecialUnit su =
                            (SpecialUnit) unit;

                    ps.setInt(1, civId);

                    ps.setString(2,
                            spcTypes[i - 7]);

                    ps.setInt(3,
                            su.getInitialArmor());

                    ps.setInt(4,
                            su.getBaseDamage());

                    ps.setInt(5,
                            su.getExperience());

                    ps.executeUpdate();
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void loadArmy(int civId,
                         Civilization civ) {

        civ.getArmy()[0] =
                loadAttackUnits(civId, "Swordsman");

        civ.getArmy()[1] =
                loadAttackUnits(civId, "Spearman");

        civ.getArmy()[2] =
                loadAttackUnits(civId, "Crossbow");

        civ.getArmy()[3] =
                loadAttackUnits(civId, "Cannon");

        civ.getArmy()[4] =
                loadDefenseUnits(civId, "ArrowTower");

        civ.getArmy()[5] =
                loadDefenseUnits(civId, "Catapult");

        civ.getArmy()[6] =
                loadDefenseUnits(civId,
                        "RocketLauncherTower");

        civ.getArmy()[7] =
                loadSpecialUnits(civId, "Magician");

        civ.getArmy()[8] =
                loadSpecialUnits(civId, "Priest");
    }



    public ArrayList<MilitaryUnit> loadAttackUnits(int civId, String wantedType) {

        ArrayList<MilitaryUnit> list =
                new ArrayList<>();

        try {

            String sql = """
                    SELECT * FROM attack_units_stats
                    WHERE civilization_id=? AND type=?
                    """;

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, civId);
            ps.setString(2, wantedType);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String type =
                        rs.getString("type");

                int armor =
                        rs.getInt("armor");

                int damage =
                        rs.getInt("base_damage");

                int exp =
                        rs.getInt("experience");

                boolean sanctified =
                        rs.getBoolean("sanctified");

                AttackUnit unit;

                switch (type) {

                    case "Swordsman":

                        unit =
                                new Swordsman(armor, damage);
                        break;

                    case "Spearman":

                        unit =
                                new Spearman(armor, damage);
                        break;

                    case "Crossbow":

                        unit =
                                new Crossbow(armor, damage);
                        break;

                    case "Cannon":

                        unit =
                                new Cannon(armor, damage);
                        break;

                    default:
                        continue;
                }

                unit.setExperience(exp);
                unit.setSanctified(sanctified);

                list.add(unit);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return list;
    }


    public ArrayList<MilitaryUnit> loadDefenseUnits(int civId, String wantedType) {

        ArrayList<MilitaryUnit> list =
                new ArrayList<>();

        try {

            String sql = """
                    SELECT * FROM defense_units_stats
                    WHERE civilization_id=? AND type=?
                    """;

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, civId);

            ps.setString(2, wantedType);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String type =
                        rs.getString("type");

                int armor =
                        rs.getInt("armor");

                int damage =
                        rs.getInt("base_damage");

                int exp =
                        rs.getInt("experience");

                boolean sanctified =
                        rs.getBoolean("sanctified");

                DefenseUnit unit;

                switch (type) {

                    case "ArrowTower":

                        unit =
                                new ArrowTower(armor, damage);
                        break;

                    case "Catapult":

                        unit =
                                new Catapult(armor, damage);
                        break;

                    case "RocketLauncherTower":

                        unit =
                                new RocketLauncherTower(
                                        armor,
                                        damage);

                        break;

                    default:
                        continue;
                }

                unit.setExperience(exp);

                unit.setSanctified(sanctified);

                list.add(unit);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return list;
    }



    public ArrayList<MilitaryUnit> loadSpecialUnits(int civId, String wantedType) {

        ArrayList<MilitaryUnit> list =
                new ArrayList<>();

        try {

            String sql = """
                    SELECT * FROM special_units_stats
                    WHERE civilization_id=? AND type=?
                    """;

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, civId);

            ps.setString(2, wantedType);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String type =
                        rs.getString("type");

                int damage =
                        rs.getInt("base_damage");

                int exp =
                        rs.getInt("experience");

                SpecialUnit unit;

                switch (type) {

                    case "Magician":

                        unit =
                                new Magician(damage);

                        break;

                    case "Priest":

                        unit =
                                new Priest(damage);

                        break;

                    default:
                        continue;
                }

                unit.setExperience(exp);

                list.add(unit);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return list;
    }
    
    
    
    public void saveBattleStats(

            int civId,
            int battleNumber,

            int woodAcquired,
            int ironAcquired,

            String winner,

            int civFoodCost,
            int civWoodCost,
            int civIronCost,

            int enemyFoodCost,
            int enemyWoodCost,
            int enemyIronCost,

            int civFoodLosses,
            int civWoodLosses,
            int civIronLosses,

            int enemyFoodLosses,
            int enemyWoodLosses,
            int enemyIronLosses,

            int rubbleWood,
            int rubbleIron) {

        try {

            String sql = """
                INSERT INTO battle_stats (

                civilization_id,
                num_battle,

                wood_acquired,
                iron_acquired,

                winner,

                civ_food_cost,
                civ_wood_cost,
                civ_iron_cost,

                enemy_food_cost,
                enemy_wood_cost,
                enemy_iron_cost,

                civ_food_losses,
                civ_wood_losses,
                civ_iron_losses,

                enemy_food_losses,
                enemy_wood_losses,
                enemy_iron_losses,

                rubble_wood,
                rubble_iron

                ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                """;

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, civId);
            ps.setInt(2, battleNumber);

            ps.setInt(3, woodAcquired);
            ps.setInt(4, ironAcquired);

            ps.setString(5, winner);

            ps.setInt(6, civFoodCost);
            ps.setInt(7, civWoodCost);
            ps.setInt(8, civIronCost);

            ps.setInt(9, enemyFoodCost);
            ps.setInt(10, enemyWoodCost);
            ps.setInt(11, enemyIronCost);

            ps.setInt(12, civFoodLosses);
            ps.setInt(13, civWoodLosses);
            ps.setInt(14, civIronLosses);

            ps.setInt(15, enemyFoodLosses);
            ps.setInt(16, enemyWoodLosses);
            ps.setInt(17, enemyIronLosses);

            ps.setInt(18, rubbleWood);
            ps.setInt(19, rubbleIron);

            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
    
    
    
    public void saveBattleUnitsStats(
    		 int civId,
    	        int battleNumber,

    	        String side,
    	        String category,
    	        String type,

    	        int initialUnits,
    	        int droppedUnits) {

    	    try {

    	        String sql = """
    	            INSERT INTO battle_units_stats (

    	            civilization_id,
    	            num_battle,

    	            side,
    	            unit_category,
    	            type,

    	            initial_units,
    	            dropped_units

    	            ) VALUES (?,?,?,?,?,?,?)
    	            """;

    	        PreparedStatement ps =
    	                conn.prepareStatement(sql);

    	        ps.setInt(1, civId);

    	        ps.setInt(2, battleNumber);

    	        ps.setString(3, side);

    	        ps.setString(4, category);

    	        ps.setString(5, type);

    	        ps.setInt(6, initialUnits);

    	        ps.setInt(7, droppedUnits);

    	        ps.executeUpdate();

    	    } catch (SQLException e) {

    	        e.printStackTrace();
    	    }

           
}
    
    
    public void saveBattleLog(

            int civId,
            int battleNumber,

            String fullLog) {

        try {

            String sql = """
                INSERT INTO battle_log (

                civilization_id,
                num_battle,
                num_line,
                log_entry

                ) VALUES (?,?,?,?)
                """;

            PreparedStatement ps =
                    conn.prepareStatement(sql);
            
            String[] lines = fullLog.split("\n");

            for(int i = 0; i < lines.length; i++) {

                ps.setInt(1, civId);

                ps.setInt(2, battleNumber);

                ps.setInt(3, i + 1);

                ps.setString(4, lines[i]);

                ps.executeUpdate();
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
    
    
    
    
    
    
    
    public void saveBattleUnitStat(

            int civId,
            int battleNumber,

            String side,
            String category,
            String type,

            int initialUnits,
            int droppedUnits
    ) {

        try {

            String sql = """
                INSERT INTO battle_units_stats(

                    civilization_id,
                    num_battle,

                    side,
                    unit_category,
                    type,

                    initial_units,
                    dropped_units
                )

                VALUES(?,?,?,?,?,?,?)
                """;

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, civId);

            ps.setInt(2, battleNumber);

            ps.setString(3, side);

            ps.setString(4, category);

            ps.setString(5, type);

            ps.setInt(6, initialUnits);

            ps.setInt(7, droppedUnits);

            ps.executeUpdate();

        } catch(SQLException e) {

            e.printStackTrace();
        }
    }
    
    
    
    public ArrayList<String[]> loadBattleReports(int civId) {

        ArrayList<String[]> reports =
                new ArrayList<>();

        try {

            String sql = """
                SELECT * 
                FROM battle_stats
                WHERE civilization_id=?
                ORDER BY num_battle DESC
                """;

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, civId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
            	
            	int battleNumber = rs.getInt("num_battle");

                String title =
                        "Battle #" +battleNumber;

                String detail1 =   "Date:\n"
                        + rs.getTimestamp("battle_date")+"\n\nWinner: " +
                        rs.getString("winner") + "\n\nCivilization Losses:\n" + "Food: "
                                + rs.getInt("civ_food_losses") + "\nWood: "
                                        + rs.getInt("civ_wood_losses")+ "\nIron: "
                                                + rs.getInt("civ_iron_losses") + "\n\nEnemy Losses:\n"+ "Food: "
                                                        + rs.getInt("enemy_food_losses")

                                                        + "\nWood: "
                                                        + rs.getInt("enemy_wood_losses")

                                                        + "\nIron: "
                                                        + rs.getInt("enemy_iron_losses")

                                                        + "\n\nRubble Collected:\n"

                                                        + "Wood: "
                                                        + rs.getInt("rubble_wood")

                                                        + "\nIron: "
                                                        + rs.getInt("rubble_iron") + "\n\n===== UNIT STATS =====\n\n"
                                                        
                                                        + loadBattleUnitStats(civId, battleNumber);
                
                String detail2 =
                		"\n\n===== BATTLE LOG =====\n\n"

        					+loadBattleLog(civId,battleNumber );

                reports.add(
                        new String[]{
                                title,
                                detail1,detail2
                        }
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return reports;
    }
    
    
    
    public String loadBattleLog(
            int civId,
            int battleNumber) {

    	String log = "";

        try {

        	String sql =
                    "SELECT log_entry "
                  + "FROM battle_log "
                  + "WHERE civilization_id=? "
                  + "AND num_battle=? "
                  + "ORDER BY num_line";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, civId);

            ps.setInt(2, battleNumber);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

            	log += rs.getString("log_entry");

            	 log += "\n";
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return log;
    }
    
    public String loadBattleUnitStats(
            int civId,
            int battleNumber) {

        String text = "";

        try {

            String sql = """
                SELECT *
                FROM battle_units_stats
                WHERE civilization_id=?
                AND num_battle=?
                ORDER BY side, unit_category
                """;

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, civId);
            ps.setInt(2, battleNumber);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                text += rs.getString("side")
                        + " - "
                        + rs.getString("type")

                        + "\nInitial Units: "
                        + rs.getInt("initial_units")

                        + "\nDropped Units: "
                        + rs.getInt("dropped_units")

                        + "\n\n";
            }

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return text;
    }
}