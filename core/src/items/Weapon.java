/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

/**
 *
 * @author Jason
 */
public class Weapon 
{
    private String name;
    private float damage;
    private float energyDrain;
    private float attackSpeed;

    /**
     * @return the name
     */
    public String getName() 
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) 
    {
        this.name = name;
    }

    /**
     * @return the damage
     */
    public float getDamage() 
    {
        return damage;
    }

    /**
     * @param damage the damage to set
     */
    public void setDamage(float damage) 
    {
        this.damage = damage;
    }

    /**
     * @return the energyDrain
     */
    public float getEnergyDrain() 
    {
        return energyDrain;
    }

    /**
     * @param energyDrain the energyDrain to set
     */
    public void setEnergyDrain(float energyDrain) 
    {
        this.energyDrain = energyDrain;
    }

    /**
     * @return the attackSpeed
     */
    public float getAttackSpeed() 
    {
        return attackSpeed;
    }

    /**
     * @param attackSpeed the attackSpeed to set
     */
    public void setAttackSpeed(float attackSpeed) 
    {
        this.attackSpeed = attackSpeed;
    }
    
   
    
}
