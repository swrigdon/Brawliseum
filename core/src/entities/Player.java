/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Jason
 */
public class Player extends Entity
{
    private String playerClass;
    private float health;
    private float energy;
    private float experience;
    private float baseAttack;
    private float baseDefense;
    private int level;
    

    @Override
    public void move() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public float attack()
    {
        
        return 0;
    }
    
    public float defense()
    {
        return 0;
    }

    /**
     * @return the health
     */
    public float getHealth() 
    {
        return health;
    }

    /**
     * @param health the health to set
     */
    public void setHealth(float health) 
    {
        this.health = health;
    }

    /**
     * @return the energy
     */
    public float getEnergy() 
    {
        return energy;
    }

    /**
     * @param energy the energy to set
     */
    public void setEnergy(float energy) 
    {
        this.energy = energy;
    }

    /**
     * @return the experience
     */
    public float getExperience() 
    {
        return experience;
    }

    /**
     * @param experience the experience to set
     */
    public void setExperience(float experience) 
    {
        this.experience = experience;
    }

    /**
     * @return the baseAttack
     */
    public float getBaseAttack() 
    {
        return baseAttack;
    }

    /**
     * @param baseAttack the baseAttack to set
     */
    public void setBaseAttack(float baseAttack) 
    {
        this.baseAttack = baseAttack;
    }

    /**
     * @return the baseDefense
     */
    public float getBaseDefense() 
    {
        return baseDefense;
    }

    /**
     * @param baseDefense the baseDefense to set
     */
    public void setBaseDefense(float baseDefense) 
    {
        this.baseDefense = baseDefense;
    }

    /**
     * @return the level
     */
    public int getLevel() 
    {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) 
    {
        this.level = level;
    }
    
    
}
