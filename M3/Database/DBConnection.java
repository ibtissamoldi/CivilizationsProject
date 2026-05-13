package M3.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import M3.game.Civilization;
import M3.interfaces.MilitaryUnit;
import M3.units.AttackUnit;
import M3.units.DefenseUnit;
import M3.units.SpecialUnit;
import M3.units.attack.Cannon;
import M3.units.attack.Crossbow;
import M3.units.attack.Spearman;
import M3.units.attack.Swordsman;
import M3.units.defense.ArrowTower;
import M3.units.defense.Catapult;
import M3.units.defense.RocketLauncherTower;
import M3.units.special.Magician;
import M3.units.special.Priest;



public class DBConnection {

	private static final String urlDatos = "jdbc:mysql://172.25.184.104:3306/civilizations?serverTimezone=UTC";
	private static final String usuario = "civ_user";
	private static final String pass = "bichos2.0";
	
	private Connection conn;

	public DBConnection() {
	    connect();
	}

    private void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println(urlDatos);
            System.out.println(usuario);
            System.out.println(pass);
            conn = DriverManager.getConnection(urlDatos,usuario,pass);
        } catch (ClassNotFoundException e) {
			System.out.println("Driver no se ha cargado correctamente!!");		
		} catch (SQLException e) {
			System.out.println("Se ha lanzado una SQLException!!");
			e.printStackTrace();
			System.exit(1);
		}
    }


    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
    
    public int CreateCivilization(String name) {
        try {
            String sql = "INSERT INTO civilization_stats (name) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.executeUpdate();
 
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                return id;
            }
        } catch (SQLException e) {
            System.out.println("Se ha lanzado una SQLException en create Civilization!!");
            e.printStackTrace();
        }
        return -1;
    }
    
    public int GetCivilizationIdByName(String name) {
        try {
            String sql = "SELECT civilization_id FROM civilization_stats WHERE name=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return rs.getInt("civilization_id");
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }



    public void UpdateCivilization(int civId, Civilization civ) {
        try {
            String update = "UPDATE civilization_stats SET " +
                            "wood_amount=?, iron_amount=?, food_amount=?, mana_amount=?, " +
                            "magicTower_counter=?, church_counter=?, farm_counter=?, " +
                            "smithy_counter=?, carpentry_counter=?, " +
                            "technology_defense_level=?, technology_attack_level=?, " +
                            "battles_counter=? " +
                            "WHERE civilization_id=?";
 
            PreparedStatement ps = conn.prepareStatement(update);
            ps.setInt(1,  civ.getWood());
            ps.setInt(2,  civ.getIron());
            ps.setInt(3,  civ.getFood());
            ps.setInt(4,  civ.getMana());
            ps.setInt(5,  civ.getMagicTower());
            ps.setInt(6,  civ.getChurch());
            ps.setInt(7,  civ.getFarm());
            ps.setInt(8,  civ.getSmithy());
            ps.setInt(9,  civ.getCarpentry());
            ps.setInt(10, civ.getTechnologyDefense());
            ps.setInt(11, civ.getTechnologyAttack());
            ps.setInt(12, civ.getBattles());
            ps.setInt(13, civId);
 
            ps.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println("Se ha lanzado una SQLException en update Civilization!!");
            e.printStackTrace();
        }
    }
    
    public Civilization LoadCivilization(int civId) {
    	
        try {
	            String query = "SELECT * FROM civilization_stats WHERE civilization_id=?";
	            PreparedStatement ps = conn.prepareStatement(query);
	            ps.setInt(1, civId);
	 
	            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Civilization civ = new Civilization();
                civ.setWood(rs.getInt("wood_amount"));
                civ.setIron(rs.getInt("iron_amount"));
                civ.setFood(rs.getInt("food_amount"));
                civ.setMana(rs.getInt("mana_amount"));
                civ.setMagicTower(rs.getInt("magicTower_counter"));
                civ.setChurch(rs.getInt("church_counter"));
                civ.setFarm(rs.getInt("farm_counter"));
                civ.setSmithy(rs.getInt("smithy_counter"));
                civ.setCarpentry(rs.getInt("carpentry_counter"));
                civ.setTechnologyDefense(rs.getInt("technology_defense_level"));
                civ.setTechnologyAttack(rs.getInt("technology_attack_level"));
                civ.setBattles(rs.getInt("battles_counter"));
                return civ;
            }
        } catch (SQLException e) {
            System.out.println("Se ha lanzado una SQLException en load Civilization!!");
            e.printStackTrace();
        }
        return null;
    }
    
    public void SaveArmy(int civId, Civilization civ) {
    	 
        try {
            String[] tables = {"attack_units_stats", "defense_units_stats", "special_units_stats"};
            for (String table : tables) {
                PreparedStatement ps = conn.prepareStatement( "DELETE FROM " + table + " WHERE civilization_id=?");
                ps.setInt(1, civId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Se ha lanzado una SQLException al borrar unidades!!");
            e.printStackTrace();
            return;
        }
 
        ArrayList<MilitaryUnit>[] army = civ.getArmy();
 
        try {
            String sqlAtk = "INSERT INTO attack_units_stats " +
                            "(civilization_id, type, armor, base_damage, experience, sanctified) " +
                            "VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sqlAtk);
 
            String[] atkTypes = {"Swordsman", "Spearman", "Crossbow", "Cannon"};
 
            for (int i = 0; i <= 3; i++) {
                for (MilitaryUnit unit : army[i]) {
                    AttackUnit au = (AttackUnit) unit;
                    ps.setInt(1,     civId);
                    ps.setString(2,  atkTypes[i]);
                    ps.setInt(3,     au.getInitialArmor());
                    ps.setInt(4,     au.getBaseDamage());
                    ps.setInt(5,     au.getExperience());
                    ps.setBoolean(6, au.isSanctified());
                    ps.executeUpdate();
                }
            }
 
        } catch (SQLException e) {
            System.out.println("Se ha lanzado una SQLException al guardar unidades de ataque!!");
            e.printStackTrace();
        }
 
        try {
            String sqlDef = "INSERT INTO defense_units_stats " +
                            "(civilization_id, type, armor, base_damage, experience, sanctified) " +
                            "VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sqlDef);
 
            String[] defTypes = {"ArrowTower", "Catapult", "RocketLauncherTower"};
 
            for (int i = 4; i <= 6; i++) {
                for (MilitaryUnit unit : army[i]) {
                    DefenseUnit du = (DefenseUnit) unit;
                    ps.setInt(1,     civId);
                    ps.setString(2,  defTypes[i - 4]);
                    ps.setInt(3,     du.getInitialArmor());
                    ps.setInt(4,     du.getBaseDamage());
                    ps.setInt(5,     du.getExperience());
                    ps.setBoolean(6, du.isSanctified());
                    ps.executeUpdate();
                }
            }
            System.out.println("Unidades de defensa guardadas.");
 
        } catch (SQLException e) {
            System.out.println("Se ha lanzado una SQLException al guardar unidades de defensa!!");
            e.printStackTrace();
        }
 
        try {
            String sqlSpc = "INSERT INTO special_units_stats " +
                            "(civilization_id, type, armor, base_damage, experience) " +
                            "VALUES (?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sqlSpc);
 
            String[] spcTypes = {"Magician", "Priest"};
 
            for (int i = 7; i <= 8; i++) {
                for (MilitaryUnit unit : army[i]) {
                    SpecialUnit su = (SpecialUnit) unit;
                    ps.setInt(1,    civId);
                    ps.setString(2, spcTypes[i - 7]);
                    ps.setInt(3,    su.getInitialArmor());
                    ps.setInt(4,    su.getBaseDamage());
                    ps.setInt(5,    su.getExperience());
                    ps.executeUpdate();
                }
            }
 
        } catch (SQLException e) {
            System.out.println("Se ha lanzado una SQLException al guardar unidades especiales!!");
            e.printStackTrace();
        }
    }
    
    public void LoadArmy(int civId, Civilization civ) {

        civ.getArmy()[0] = LoadAttackUnits(civId, "Swordsman");
        civ.getArmy()[1] = LoadAttackUnits(civId, "Spearman");
        civ.getArmy()[2] = LoadAttackUnits(civId, "Crossbow");
        civ.getArmy()[3] = LoadAttackUnits(civId, "Cannon");

        civ.getArmy()[4] = LoadDefenseUnits(civId, "ArrowTower");
        civ.getArmy()[5] = LoadDefenseUnits(civId, "Catapult");
        civ.getArmy()[6] = LoadDefenseUnits(civId, "RocketLauncherTower");

        civ.getArmy()[7] = LoadSpecialUnits(civId, "Magician");
        civ.getArmy()[8] = LoadSpecialUnits(civId, "Priest");
    }

    public Civilization LoadCivilizationComplete(int civId) {

        Civilization civ = LoadCivilization(civId);

        if(civ != null) {
            LoadArmy(civId, civ);
        }

        return civ;
    }
    
    public ArrayList<MilitaryUnit> LoadAttackUnits(int civId, String wantedType) {
        ArrayList<MilitaryUnit> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM attack_units_stats WHERE civilization_id=? AND type=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, civId);
            ps.setString(2, wantedType);
            ResultSet rs = ps.executeQuery();
 
            while (rs.next()) {
                String type = rs.getString("type");
                int armor      = rs.getInt("armor");
                int baseDamage = rs.getInt("base_damage");
                int experience = rs.getInt("experience");
                boolean sanctified = rs.getBoolean("sanctified");
 
                AttackUnit unit;
                switch (type) {
                    case "Swordsman": unit = new Swordsman(armor, baseDamage); break;
                    case "Spearman":  unit = new Spearman(armor, baseDamage);  break;
                    case "Crossbow":  unit = new Crossbow(armor, baseDamage);  break;
                    case "Cannon":    unit = new Cannon(armor, baseDamage);    break;
                    default: continue;
                }
                unit.setExperience(experience);
                unit.setSanctified(sanctified);
                list.add(unit);
            } 
        } catch (SQLException e) {
            System.out.println("Se ha lanzado una SQLException en loadAttackUnits!!");
            e.printStackTrace();
        }
        return list;
    }
    
    
    public ArrayList<MilitaryUnit> LoadDefenseUnits(int civId,String wantedType) {
        ArrayList<MilitaryUnit> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM defense_units_stats WHERE civilization_id=? AND type=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, civId);
            ps.setString(2, wantedType);
            ResultSet rs = ps.executeQuery();
 
            while (rs.next()) {
                String type    = rs.getString("type");
                int armor      = rs.getInt("armor");
                int baseDamage = rs.getInt("base_damage");
                int experience = rs.getInt("experience");
                boolean sanctified = rs.getBoolean("sanctified");
 
                DefenseUnit unit;
                switch (type) {
                    case "ArrowTower":          unit = new ArrowTower(armor, baseDamage);          break;
                    case "Catapult":            unit = new Catapult(armor, baseDamage);            break;
                    case "RocketLauncherTower": unit = new RocketLauncherTower(armor, baseDamage); break;
                    default: continue;
                }
                unit.setExperience(experience);
                unit.setSanctified(sanctified);
                list.add(unit);
            }
 
        } catch (SQLException e) {
            System.out.println("Se ha lanzado una SQLException en loadDefenseUnits!!");
            e.printStackTrace();
        }
        return list;
    }
    
    
    
    public ArrayList<MilitaryUnit> LoadSpecialUnits(int civId, String wantedType) {

        ArrayList<MilitaryUnit> list = new ArrayList<>();

        try {

            String query =
            "SELECT * FROM special_units_stats WHERE civilization_id=? AND type=?";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, civId);
            ps.setString(2, wantedType);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                String type = rs.getString("type");

                int armor = rs.getInt("armor");
                int baseDamage = rs.getInt("base_damage");
                int experience = rs.getInt("experience");

                SpecialUnit unit;

                switch(type) {

                    case "Magician":
                        unit = new Magician(baseDamage);
                        break;

                    case "Priest":
                        unit = new Priest(baseDamage);
                        break;

                    default:
                        continue;
                }

                unit.setExperience(experience);

                list.add(unit);
            }

        } catch(SQLException e) {

            System.out.println("SQLException loading special units");
            e.printStackTrace();
        }

        return list;
    }
    
 

}