/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import dungeon.DungeonTile;
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
    
    private int playerDirection;
    

    public Player(int x, int y, Texture playerTexture, Level currentLevel, String playerClass)
    {
        this.setEntityTexture(playerTexture);
        this.setX(x);
        this.setY(y);
        this.setxLocation(x);
        this.setyLocation(y);
        this.setCurrentLevel(currentLevel);
        this.setSpeed(3);
        this.setPlayerClass(playerClass);
        
        this.setPlayerDirection(0);
        
        this.setMovingX(false);
        this.setMovingY(false);
        this.setMovingNX(false);
        this.setMovingNY(false);
        
        this.set(x, y, (float)playerTexture.getWidth()/32, (float)playerTexture.getHeight()/32);
    }
    
    @Override
    public void move(DungeonTile[][] map) 
    {
        //Added by Jason
        if(Gdx.input.isKeyPressed(Input.Keys.W))
        {
            this.setyLocation(this.getyLocation() + this.getSpeed()*Gdx.graphics.getDeltaTime());
            this.setMovingY(true);
            this.setPlayerDirection(0);
            
            this.setMovingX(false);
            this.setMovingNY(false);
            this.setMovingNX(false);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S))
        {
            this.setyLocation(this.getyLocation() - this.getSpeed()*Gdx.graphics.getDeltaTime());
            this.setMovingNY(true);
            this.setPlayerDirection(2);
            
            this.setMovingX(false);
            this.setMovingY(false);
            this.setMovingNX(false);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D))
        {
            this.setxLocation(this.getxLocation() + this.getSpeed()*Gdx.graphics.getDeltaTime());
            this.setMovingX(true);
            this.setPlayerDirection(1);
            
            this.setMovingY(false);
            this.setMovingNY(false);
            this.setMovingNX(false);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A))
        {
            this.setxLocation(this.getxLocation() - this.getSpeed()*Gdx.graphics.getDeltaTime());
            this.setMovingNX(true);
            this.setPlayerDirection(3);
            
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
        
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            if(playerClass.equals("sword"))
            {
                swordAttack(map);
            }
            else if(playerClass.equals("bow"))
            {
                bowAttack();
            }
        }
    }
    
    private void swordAttack(DungeonTile[][] map)
    {
        if(playerDirection == 0)
        {
            if(map[(int)this.getxLocation()][(int)this.getyLocation()+1].getEnemyOnTile() != null)
            {
                System.out.println("Enemy above me");
                map[(int)this.getxLocation()][(int)this.getyLocation()+1].getEnemyOnTile().getHit(attack());
            }
            else if(map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile() != null)
            {
                System.out.println("Enemy at me 0");
                map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile().getHit(attack());
            }
        }
        else if(playerDirection == 1)
        {
            if(map[(int)this.getxLocation()+1][(int)this.getyLocation()].getEnemyOnTile() != null)
            {
                System.out.println("Enemy to the right of me");
                map[(int)this.getxLocation()+1][(int)this.getyLocation()].getEnemyOnTile().getHit(attack());
            }
            else if(map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile() != null)
            {
                System.out.println("Enemy at me 1");
                map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile().getHit(attack());
            }
        }
        else if(playerDirection == 2)
        {
            if(map[(int)this.getxLocation()][(int)this.getyLocation()-1].getEnemyOnTile() != null)
            {
                System.out.println("Enemy below me");
                map[(int)this.getxLocation()][(int)this.getyLocation()-1].getEnemyOnTile().getHit(attack());
            }
            else if(map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile() != null)
            {
                System.out.println("Enemy at me 2");
                map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile().getHit(attack());
            }
        }
        else if(playerDirection == 3)
        {
            if(map[(int)this.getxLocation()-1][(int)this.getyLocation()].getEnemyOnTile() != null)
            {
                System.out.println("Enemy to the left of me");
                map[(int)this.getxLocation()-1][(int)this.getyLocation()].getEnemyOnTile().getHit(attack());
            }
            else if(map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile() != null)
            {
                System.out.println("Enemy at me 3");
                map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile().getHit(attack());
            }
        }
    }
    private void bowAttack()
    {
        
    }
    
    public float attack()
    {
        return 100;
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

    /**
     * @return the playerDirection
     */
    public int getPlayerDirection() {
        return playerDirection;
    }

    /**
     * @param playerDirection the playerDirection to set
     */
    public void setPlayerDirection(int playerDirection) {
        this.playerDirection = playerDirection;
    }
}
