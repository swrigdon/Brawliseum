/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import dungeon.Level;

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
    

    public Player(int x, int y, Texture playerTexture, Level currentLevel)
    {
        this.setEntityTexture(playerTexture);
        this.setX(x);
        this.setY(y);
        this.setxLocation(x);
        this.setyLocation(y);
        this.setCurrentLevel(currentLevel);
        this.setSpeedx(5);
        this.setSpeedy(5);
        
        this.setMovingX(false);
        this.setMovingY(false);
        this.setMovingNX(false);
        this.setMovingNY(false);
        
        this.set(x, y, (float)playerTexture.getWidth()/32, (float)playerTexture.getHeight()/32);
    }
    
    @Override
    public void move() 
    {
        //Added by Jason
        if(Gdx.input.isKeyPressed(Input.Keys.W))
        {
            this.setyLocation(this.getyLocation() + this.getSpeedy()*Gdx.graphics.getDeltaTime());
            this.setMovingY(true);
            this.setMovingX(false);
            this.setMovingNY(false);
            this.setMovingNX(false);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S))
        {
            this.setyLocation(this.getyLocation() - this.getSpeedy()*Gdx.graphics.getDeltaTime());
            this.setMovingNY(true);
            this.setMovingX(false);
            this.setMovingY(false);
            this.setMovingNX(false);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D))
        {
            this.setxLocation(this.getxLocation() + this.getSpeedx()*Gdx.graphics.getDeltaTime());
            this.setMovingX(true);
            this.setMovingY(false);
            this.setMovingNY(false);
            this.setMovingNX(false);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A))
        {
            this.setxLocation(this.getxLocation() - this.getSpeedx()*Gdx.graphics.getDeltaTime());
            this.setMovingNX(true);
            this.setMovingY(false);
            this.setMovingX(false);
            this.setMovingNY(false);

        }
        else
        {
            this.setMovingX(false);
            this.setMovingY(false);
            this.setMovingNY(false);
            this.setMovingNX(false);
        }
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

    /**
     * @return the playerClass
     */
    public String getPlayerClass() {
        return playerClass;
    }

    /**
     * @param playerClass the playerClass to set
     */
    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }
}
