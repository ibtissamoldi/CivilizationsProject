package M3.units;

import M3.interfaces.*;

public abstract class SpecialUnit implements MilitaryUnit, Variables{
    private int armor;
	private int initialArmor;
	private int baseDamage;
	private int experience;

    public SpecialUnit(int baseDamage) {
        this.armor = 0;
        this.initialArmor = 0;
        this.baseDamage = baseDamage;
        this.experience = 0;
    }

    public int getArmor() {
        return armor;
    }
    
    
    public int getInitialArmor() {
		return initialArmor;
	}

	public int getBaseDamage() {
        return baseDamage;
    }
    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getExperience() {
        return experience;
    }

    public int attack() {
        int damage = baseDamage;

        damage += (damage * experience * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT) / 100;

        return damage;
    }

    public void takeDamage(int receivedDamage) {
        armor -= receivedDamage;
        if(armor < 0) {
	        armor = 0;
	    }
    }

    public int getActualArmor() {
        return armor;
    }

    public void resetArmor() {
        armor = 0;
    }
    
    public void setArmor(int n) {
    	this.armor = n;
    }
}
