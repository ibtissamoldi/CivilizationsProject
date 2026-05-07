package units;

import interfaces.*;

public abstract class DefenseUnit implements MilitaryUnit, Variables{

    private int armor;
	private int initialArmor;
	private int baseDamage;
	private int experience;
	private boolean sanctified;

    public DefenseUnit(int armor, int baseDamage) {
        this.armor = armor;
        this.initialArmor = armor;
        this.baseDamage = baseDamage;
        this.experience   = 0;
        this.sanctified   = false;
    }

    //getters and setters

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
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

    public boolean isSanctified() {
        return sanctified;
    }

    public void setSanctified(boolean sanctified) {
        this.sanctified = sanctified;
    }

    //common methods in all units

    public int getExperience() {
        return experience;
    }

     public int attack() {
        int damage = baseDamage;
        damage += (damage * experience * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT) / 100;

        if (sanctified) {
            damage += (damage * PLUS_ATTACK_UNIT_SANCTIFIED) / 100;
        }

        return damage;
    }

    public void takeDamage(int receivedDamage) {
        armor -= receivedDamage;
    }

    public int getActualArmor() {
        return armor;
    }

    public void resetArmor() {
        int newArmor = initialArmor;

    	newArmor += (newArmor * experience * PLUS_ARMOR_UNIT_PER_EXPERIENCE_POINT) / 100;

		if (sanctified) {
			newArmor += (newArmor * PLUS_ARMOR_UNIT_SANCTIFIED) / 100;
		}

    	armor = newArmor;
    }


    
}