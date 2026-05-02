package M3.units;

import M3.interfaces.*;

public abstract class SpecialUnit implements MilitaryUnit, Variables{
    private int armor;
	private int initialArmor;
	private int baseDamage;
	private int experience;
    public SpecialUnit(int armor,int baseDamage) {
        this.armor = 0;
        this.initialArmor = 0;
        this.baseDamage = baseDamage;
        this.experience = 0;
    }
    public int getArmor() {
        return armor;
    }
    public void setArmor(int armor) {
        this.armor = armor;
    }
    public int getInitialArmor() {
        return initialArmor;
    }
    public void setInitialArmor(int initialArmor) {
        this.initialArmor = initialArmor;
    }
    public int getBaseDamage() {
        return baseDamage;
    }
    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }
    public void setExperience(int experience) {
        this.experience = experience;
    }

    

    

    
}
